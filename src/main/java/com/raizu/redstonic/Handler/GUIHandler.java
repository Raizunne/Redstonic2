package com.raizu.redstonic.Handler;

import com.raizu.redstonic.GUI.ModifierContainer;
import com.raizu.redstonic.GUI.ModifierGUI;
import com.raizu.redstonic.Redstonic;
import com.raizu.redstonic.TileEntity.TERedstonicModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

/**
 * Created by Raizunne as a part of Redstonic2
 * on 06/03/2016, 07:45 PM.
 */
public class GUIHandler implements IGuiHandler {

   public GUIHandler(){
      NetworkRegistry.INSTANCE.registerGuiHandler(Redstonic.instance, this);
   }


   @Override
   public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
      TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
      switch(ID){
         case 0: if(te!=null && te instanceof TERedstonicModifier){
            return new ModifierContainer(player.inventory, (TERedstonicModifier)te);
         }
         break;
      }
      return null;
   }

   @Override
   public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
      TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
      switch(ID){
         case 0: if(te!=null && te instanceof TERedstonicModifier){
            return new ModifierGUI(player.inventory, (TERedstonicModifier)te);
         }
         break;
      }
      return null;
   }
}
