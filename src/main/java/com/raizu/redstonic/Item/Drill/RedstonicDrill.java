package com.raizu.redstonic.Item.Drill;

import com.raizu.redstonic.Item.PowerBattery;
import com.raizu.redstonic.Redstonic;
import com.raizu.redstonic.RedstonicItems;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raizunne as a part of Redstonic2
 * on 03/03/2016, 07:11 PM.
 */
public class RedstonicDrill extends ItemPickaxe{

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

   @Override
   public String getUnlocalizedName() {return "RedstonicDrill";}

   @Override
   public boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, BlockPos pos, EntityLivingBase playerIn) {

      return super.onBlockDestroyed(stack, worldIn, blockIn, pos, playerIn);
   }

   @Override
   public float getDigSpeed(ItemStack stack, IBlockState state) {
      return head.speed;
   }

   @Override
   public boolean isDamageable() {
      return false;
   }

   @SideOnly(Side.CLIENT)
   public ResourceLocation[] getItemVariants(){
      String loc = Redstonic.MODID + ":RedstonicDrill";
      List<ResourceLocation> variants = new ArrayList<ResourceLocation>();
      for(String heads : RedstonicItems.drills.keySet()){
         for(String bodies : RedstonicItems.bodies.keySet()){
            for(String mod1 : RedstonicItems.drillMods.keySet()){
               for(String mod2 : RedstonicItems.drillMods.keySet()){
                  for(String mod3 : RedstonicItems.drillMods.keySet()){
                     String newVariant = String.format("head=%s,body=%s,mod1=%s,mod2=%s,mod3=%s", heads, bodies, mod1, mod2, mod3);
                     variants.add(new ModelResourceLocation(loc, newVariant));
                  }
               }
            }
         }
      }
      return variants.toArray(new ResourceLocation[variants.size()]);
   }

   @SideOnly(Side.CLIENT)
   public ModelResourceLocation getModelLocation(ItemStack stack){
      String loc = Redstonic.MODID + ":RedstonicDrill";
      String[] mods = {stack.getTagCompound()==null ? "none" : stack.getTagCompound().getString("mod1"), stack.getTagCompound()==null ? "none" : stack.getTagCompound().getString("mod2"), stack.getTagCompound()==null ? "none" : stack.getTagCompound().getString("mod3")};
      String variant = "head="+"Diamond"+",body="+"Iron"+",mod1="+mods[0]+",mod2="+mods[1]+",mod3="+mods[2];
      return new ModelResourceLocation(loc, variant);
   }
}
