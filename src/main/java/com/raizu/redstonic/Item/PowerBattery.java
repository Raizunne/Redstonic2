package com.raizu.redstonic.Item;

import cofh.api.energy.IEnergyContainerItem;
import com.raizu.redstonic.Redstonic;
import com.raizu.redstonic.Util.StringUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import java.text.NumberFormat;
import java.util.List;
import java.util.Random;

/**
 * Created by Raizunne as a part of Redstonic2
 * on 03/03/2016, 07:12 PM.
 */
public class PowerBattery extends Item implements IEnergyContainerItem{

   public String name;
   public int max;
   public int maxReceive;

   public PowerBattery(String name, int max, int maxReceive){
      this.name = name;
      this.max = max;
      this.maxReceive = maxReceive;
      this.setCreativeTab(Redstonic.redTab);
      this.setMaxStackSize(1);
      this.setUnlocalizedName(getUnlocalizedName());
      this.setRegistryName(name.toLowerCase()+"RedstonicBattery");
   }

   public void setDefaults(ItemStack stack){
      if(stack.getTagCompound()==null){
         NBTTagCompound tag = new NBTTagCompound();
         tag.setInteger("max", max);
         tag.setInteger("Energy", 0);
         tag.setString("name", name);
         tag.setInteger("maxReceive", maxReceive);
         stack.setTagCompound(tag);
      }
   }

   @Override
   public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
      setDefaults(stack);
   }

   @Override
   public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      setDefaults(stack);
   }

   @Override
   public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
      if(stack.getTagCompound()!=null && !stack.getTagCompound().getString("name").equals("Absolute")){
         NBTTagCompound tag = stack.getTagCompound();
         tooltip.add("Charge: " + StringUtils.progressBar(tag.getInteger("Energy"), tag.getInteger("max"), 30) + " " + (tag.getInteger("Energy")/tag.getInteger("max"))*100+"%");
         tooltip.add("-   " + EnumChatFormatting.YELLOW + NumberFormat.getInstance().format(tag.getInteger("Energy")) + EnumChatFormatting.GRAY +"/" + EnumChatFormatting.YELLOW + NumberFormat.getInstance().format(tag.getInteger("max")) + EnumChatFormatting.GRAY + " RF");
         tooltip.add("-   In/Out: " + NumberFormat.getInstance().format(tag.getInteger("maxReceive")) + "RF/t");
      }else if(stack.getTagCompound()!=null && stack.getTagCompound().getString("name").equals("Absolute")){
         tooltip.add("Charge: [" + EnumChatFormatting.LIGHT_PURPLE +  "==/==/==/==" + EnumChatFormatting.GRAY + "] " + "101%");
         tooltip.add("-   §k10000§r§7 / §k10000§r§7 RF");
         tooltip.add("-   In/Out: " + NumberFormat.getInstance().format(stack.getTagCompound().getInteger("maxReceive")) + "RF/t");
      }else if(stack.getTagCompound()==null){
         if(max==-1){
            tooltip.add("Max charge: §k10000§r§7 / §k10000§r§7 RF");
         }else {
            tooltip.add("Max charge: " + NumberFormat.getInstance().format(max));
         }
         tooltip.add("In/Out: " + NumberFormat.getInstance().format(maxReceive));
      }
   }

   @Override
   public String getUnlocalizedName() {
      return name.toLowerCase() + "RedstonicBattery";
   }

   @Override
   public int receiveEnergy(ItemStack stack, int maxReceive, boolean simulate) {
      if(stack.getTagCompound()==null){
         setDefaults(stack);
      }
      NBTTagCompound tag = stack.getTagCompound();
      int energyStored = tag.getInteger("Energy");
      int energyReceived = Math.min(tag.getInteger("max") - energyStored, Math.min(tag.getInteger("maxReceive"), maxReceive));
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
