package com.raizu.redstonic.GUI;

import com.raizu.redstonic.TileEntity.TERedstonicModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Raizunne as a part of Redstonic2
 * on 06/03/2016, 07:24 PM.
 */
public class ModifierContainer extends Container {

   private TERedstonicModifier tileentity;

   public ModifierContainer(InventoryPlayer invplayer, TERedstonicModifier modifier){
      this.tileentity = modifier;
      for (int x = 0; x < 9; x++) {
         addSlotToContainer(new Slot(invplayer, x, 8 + 18 * x, 147));
      }

      for (int y = 0; y < 3; y++) {
         for (int x = 0; x < 9; x++) {
            addSlotToContainer(new Slot(invplayer, x + y * 9 + 9, 8 + 18 * x, 89 + y * 18));
         }
      }

      addSlotToContainer(new Slot(tileentity, 0, 33, 42));
      addSlotToContainer(new Slot(tileentity, 1, 67, 19));
      addSlotToContainer(new Slot(tileentity, 2, 67, 42));
      addSlotToContainer(new Slot(tileentity, 3, 67, 65));
   }

   @Override
   public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
      return null;
   }

   @Override
   public boolean canInteractWith(EntityPlayer playerIn) {
      return tileentity.isUseableByPlayer(playerIn);
   }
}
