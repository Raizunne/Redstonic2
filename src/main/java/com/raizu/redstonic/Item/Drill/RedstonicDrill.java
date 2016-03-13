package com.raizu.redstonic.Item.Drill;

import cofh.api.energy.IEnergyContainerItem;
import com.raizu.redstonic.Item.PowerBattery;
import com.raizu.redstonic.Redstonic;
import com.raizu.redstonic.RedstonicItems;
import com.raizu.redstonic.Util.StringUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raizunne as a part of Redstonic2
 * on 03/03/2016, 07:11 PM.
 */
public class RedstonicDrill extends ItemPickaxe implements IEnergyContainerItem{

   DrillHead head;
   DrillBody body;
   PowerBattery power;

   public RedstonicDrill(){
      super(ToolMaterial.EMERALD);
      setUnlocalizedName(getUnlocalizedName());
      setNoRepair();
      setMaxDamage(80);
      setCreativeTab(Redstonic.redTab);
      this.setRegistryName(getUnlocalizedName());
   }

   public void setDefaults(ItemStack stack){

   }

   @Override
   public String getUnlocalizedName() {return "RedstonicDrill";}

   @Override
   public boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, BlockPos pos, EntityLivingBase playerIn) {
      fixDurability(stack);
      if(isHead(stack, "Heavy")){
         System.out.println("HEAVY TRY");
         threebythree((EntityPlayer)playerIn, worldIn, pos, blockIn, stack);
      }
      return true;
   }

   public void threebythree(EntityPlayer player, World world, BlockPos pos, Block block, ItemStack stack){
      int ogx = pos.getX(); int ogy = pos.getY(); int ogz = pos.getZ();
      NBTTagCompound tag = stack.getTagCompound();
      MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(world, player, false);
      int x = pos.getX();
      int y = pos.getY();
      int z = pos.getZ();
      if(mop !=null && !world.isRemote){
         if(mop.sideHit== EnumFacing.UP || mop.sideHit==EnumFacing.DOWN){
            x = x-1; z = z-1;
            for(int i=0; i<=2; i++){ for(int r=0; r<=2; r++){
               if(checkHeavy(world, block, ogx, ogy, ogz, x+i, y, z+r)) {
                  tag.setInteger("blocks", tag.getInteger("blocks") + 1);
                  world.destroyBlock(new BlockPos(x + i, y, z + r), true);
               }
            }}
         }else if(mop.sideHit==EnumFacing.NORTH || mop.sideHit==EnumFacing.SOUTH){
            x=x+1; y=y+1;
            for(int i=0; i<=2; i++){ for(int r=0; r<=2; r++){
               if(checkHeavy(world, block, ogx, ogy, ogz, x-i, y-r, z)) {
                  tag.setInteger("blocks", tag.getInteger("blocks")+1);
                  world.destroyBlock(new BlockPos(x - i, y - r, z), true);
               }
            }}
         }else if(mop.sideHit==EnumFacing.WEST || mop.sideHit==EnumFacing.EAST){
            z=z+1; y=y+1;
            for(int i=0; i<=2; i++){ for(int r=0; r<=2; r++){
               if(checkHeavy(world, block, ogx, ogy, ogz, x, y-r, z-i)) {
                  tag.setInteger("blocks", tag.getInteger("blocks")+1);
                  world.destroyBlock(new BlockPos(x, y - r, z - i), true);
               }
            }}
         }
      }
   }

   public boolean checkHeavy(World world, Block block, int x, int y, int z, int bx, int by, int bz){
      Block breakingBlock = world.getGroundAboveSeaLevel(new BlockPos(bx, by, bz));
      boolean checkHardness = breakingBlock.getBlockHardness(world, new BlockPos(bx, by, bz))<=block.getBlockHardness(world, new BlockPos(x, y, z))+10F;
      boolean checkTE = !breakingBlock.hasTileEntity(breakingBlock.getDefaultState());
      return checkHardness && checkTE;
   }

   @Override
   public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
      if(stack.getTagCompound()==null){

      }else{
         NBTTagCompound tag = stack.getTagCompound();
         tooltip.add("Charge: " + StringUtils.progressBar(tag.getInteger("Energy"), tag.getInteger("maxEnergy"), 30) + " " + (tag.getInteger("Energy")/tag.getInteger("maxEnergy"))*100+"%");
         tooltip.add("-   " + EnumChatFormatting.YELLOW + NumberFormat.getInstance().format(tag.getInteger("Energy")) + EnumChatFormatting.GRAY +"/" + EnumChatFormatting.YELLOW + NumberFormat.getInstance().format(tag.getInteger("maxEnergy")) + EnumChatFormatting.GRAY + " RF");
      }
   }

   public void fixDurability(ItemStack stack){
      int energy = stack.getTagCompound().getInteger("Energy");
      int maxEnergy = stack.getTagCompound().getInteger("maxEnergy");
      int durability =(energy*80)/maxEnergy;
      stack.setItemDamage(durability);
   }

   @Override
   public float getDigSpeed(ItemStack stack, IBlockState state) {
      return RedstonicItems.drills.get(stack.getTagCompound().getString("head")).speed;
   }

   @Override
   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
      System.out.println(itemStackIn.getTagCompound());
      return super.onItemRightClick(itemStackIn, worldIn, playerIn);
   }

   public static boolean isHead(ItemStack stack, String head){
      return stack.getTagCompound().getString("head").equals(head);
   }

   @Override
   public boolean isDamageable() {
      return false;
   }

   @Override
   public int receiveEnergy(ItemStack stack, int maxReceive, boolean simulate) {
      if(stack.getTagCompound()==null){
         setDefaults(stack);
      }
      NBTTagCompound tag = stack.getTagCompound();
      int energyStored = tag.getInteger("Energy");
      int energyReceived = Math.min(tag.getInteger("maxEnergy") - energyStored, Math.min(tag.getInteger("maxReceive"), maxReceive));
      if (!simulate) {
         energyStored += energyReceived;
         tag.setInteger("Energy", energyStored);
         stack.setTagCompound(tag);
      }
      return energyReceived;
   }

   @Override
   public int extractEnergy(ItemStack stack, int maxExtract, boolean simulate) {
      return 0;
   }

   @Override
   public int getEnergyStored(ItemStack stack) {
      if(stack.getTagCompound()==null || stack.getTagCompound().hasKey("Energy")){
         return 0;
      }
      return stack.getTagCompound().getInteger("Energy");
   }

   @Override
   public int getMaxEnergyStored(ItemStack stack) {
      if(stack.getTagCompound()==null || stack.getTagCompound().hasKey("maxReceive")){
         return 0;
      }
      return stack.getTagCompound().getInteger("maxReceive");
   }
}
