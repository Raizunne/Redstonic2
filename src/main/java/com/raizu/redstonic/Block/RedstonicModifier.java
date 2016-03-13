package com.raizu.redstonic.Block;

import com.raizu.redstonic.Redstonic;
import com.raizu.redstonic.TileEntity.TERedstonicModifier;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

/**
 * Created by Raizunne as a part of Redstonic2
 * on 06/03/2016, 07:31 PM.
 */
public class RedstonicModifier extends BlockContainer {

   public RedstonicModifier(){
      super(Material.ground);
      this.setCreativeTab(Redstonic.redTab);
      this.setHardness(1F);
      this.setUnlocalizedName(getUnlocalizedName());
      this.setRegistryName(getUnlocalizedName());
   }

   @Override
   public String getUnlocalizedName() {
      return "RedstonicModifier";
   }

   @Override
   public TileEntity createNewTileEntity(World worldIn, int meta) {
      return new TERedstonicModifier();
   }

   @Override
   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
      if(!worldIn.isRemote && !playerIn.isSneaking()){
         FMLNetworkHandler.openGui(playerIn, Redstonic.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
      }
      return true;
   }
}
