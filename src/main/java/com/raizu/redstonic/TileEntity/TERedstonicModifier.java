package com.raizu.redstonic.TileEntity;

import com.raizu.redstonic.Item.Augment;
import com.raizu.redstonic.Item.Drill.DrillBody;
import com.raizu.redstonic.Item.Drill.DrillHead;
import com.raizu.redstonic.Item.Drill.RedstonicDrill;
import com.raizu.redstonic.Item.PowerBattery;
import com.raizu.redstonic.Redstonic;
import com.raizu.redstonic.RedstonicItems;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import scala.tools.nsc.interpreter.Power;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raizunne as a part of Redstonic2
 * on 06/03/2016, 06:18 PM.
 */
public class TERedstonicModifier extends TileEntity implements IInventory {

   ItemStack[] items;
   private int augments;
   private Augment aug1, aug2, aug3;
   private String customName;

   public TERedstonicModifier(){
      this.items = new ItemStack[8];
      this.customName = "";
   }

   public void craft(String rename){
      ItemStack[] augs = {items[4], items[5], items[6]};
      System.out.println(getStackInSlot(1)!=null && getStackInSlot(1).getItem() instanceof DrillHead && getStackInSlot(2)!=null && getStackInSlot(1).getItem() instanceof DrillBody && getStackInSlot(3)!=null && getStackInSlot(3).getItem() instanceof PowerBattery);
      if(getStackInSlot(1)!=null && getStackInSlot(1).getItem() instanceof DrillHead && getStackInSlot(2)!=null && getStackInSlot(2).getItem() instanceof DrillBody && getStackInSlot(3)!=null && getStackInSlot(3).getItem() instanceof PowerBattery){

         ItemStack newDrill = new ItemStack(RedstonicItems.redstonicDrill);
         NBTTagCompound tag = new NBTTagCompound();
         tag.setString("head", ((DrillHead)items[1].getItem()).name);
         tag.setString("body", ((DrillBody)items[2].getItem()).name);
         tag.setString("battery", ((PowerBattery)items[3].getItem()).name);
         tag.setInteger("maxEnergy", ((PowerBattery) items[3].getItem()).max);
         tag.setInteger("maxReceive", ((PowerBattery) items[3].getItem()).maxReceive);
         tag.setInteger("Energy", items[3].getTagCompound().getInteger("Energy"));
         newDrill.setTagCompound(tag);

         if(!rename.equals("")){
            newDrill.setStackDisplayName(rename);
         }

         removeStackFromSlot(1);
         removeStackFromSlot(2);
         removeStackFromSlot(3);

         setInventorySlotContents(0, newDrill);
      }
   }

   public void deconstruct(String rename){
      if(getStackInSlot(0)!=null && getStackInSlot(0).getItem() instanceof RedstonicDrill && getStackInSlot(1)==null && getStackInSlot(2)==null && getStackInSlot(3)==null){
         ItemStack drill = getStackInSlot(0);

         ItemStack head = new ItemStack(RedstonicItems.drills.get(drill.getTagCompound().getString("head")));
         ItemStack body = new ItemStack(RedstonicItems.bodies.get(drill.getTagCompound().getString("body")));
         ItemStack power = new ItemStack(RedstonicItems.power.get(drill.getTagCompound().getString("battery")));

         NBTTagCompound tag = new NBTTagCompound();
         tag.setInteger("Energy", drill.getTagCompound().getInteger("Energy"));
         tag.setInteger("max", drill.getTagCompound().getInteger("maxEnergy"));
         tag.setInteger("maxReceive", drill.getTagCompound().getInteger("maxReceive"));
         power.setTagCompound(tag);

         removeStackFromSlot(0);
         setInventorySlotContents(1, head);
         setInventorySlotContents(2, body);
         setInventorySlotContents(3, power);
      }
   }

   public boolean hasDuplicateAugments(ItemStack[] augs){
      List<Item> goodAugs = new ArrayList<Item>(augs.length);
      for(ItemStack aug : augs){
         if(aug!=null){
            if(goodAugs.contains(aug.getItem())){
               return true;
            }else{
               goodAugs.add(aug.getItem());
            }
         }
      }
      return false;
   }

   @Override
   public int getSizeInventory() {
      return items.length;
   }

   @Override
   public ItemStack getStackInSlot(int index) {
      return items[index];
   }

   @Override
   public ItemStack decrStackSize(int index, int count) {
      ItemStack itemstack = getStackInSlot(index);

      if(itemstack != null){
         if(itemstack.stackSize <= count){
            setInventorySlotContents(index, null);
         }else{
            itemstack = itemstack.splitStack(count);
            markDirty();
         }
      }
      return itemstack;
   }

   @Override
   public ItemStack removeStackFromSlot(int index) {
      if (this.items[index] != null){
         ItemStack itemstack = this.items[index];
         this.items[index] = null;
         return itemstack;
      }else{
         return null;
      }
   }

   @Override
   public void setInventorySlotContents(int index, ItemStack stack) {
      items[index] = stack;
   }

   @Override
   public int getInventoryStackLimit() {
      return 64;
   }

   @Override
   public boolean isUseableByPlayer(EntityPlayer player) {
      return player.getDistanceSq(getPos().getX() + 0.5, getPos().getY() + 0.5, getPos().getZ() + 0.5) <= 64;
   }

   @Override
   public void openInventory(EntityPlayer player) {

   }

   @Override
   public void closeInventory(EntityPlayer player) {

   }

   @Override
   public boolean isItemValidForSlot(int index, ItemStack stack) {
      return true;
   }

   @Override
   public int getField(int id) {
      return 0;
   }

   @Override
   public void setField(int id, int value) {

   }

   @Override
   public int getFieldCount() {
      return 0;
   }

   @Override
   public void clear() {
      for (int i = 0; i < items.length; i++) {
         items[i] = null;
      }
   }

   @Override
   public String getName() {
      return "Redstonic Furnace";
   }

   @Override
   public boolean hasCustomName() {
      return false;
   }

   @Override
   public IChatComponent getDisplayName() {
      return null;
   }
}
