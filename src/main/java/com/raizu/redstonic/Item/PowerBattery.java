package com.raizu.redstonic.Item;

import com.raizu.redstonic.Redstonic;
import com.raizu.redstonic.Util.StringUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

/**
 * Created by Raizunne as a part of Redstonic2
 * on 03/03/2016, 07:12 PM.
 */
public class PowerBattery extends Item {

   public String name;
   public int max;

   public PowerBattery(String name, int max){
      this.name = name;
      this.max = max;
      this.setCreativeTab(Redstonic.redTab);
      this.setMaxStackSize(1);
      this.setUnlocalizedName(getUnlocalizedName());
      this.setRegistryName(name.toLowerCase()+"RedstonicBattery");
   }

   public void setDefaults(ItemStack stack){
      if(stack.getTagCompound()==null){
         NBTTagCompound tag = new NBTTagCompound();
         tag.setInteger("max", max);
         tag.setInteger("current", 0);
         tag.setString("name", name);
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
         tooltip.add("Charge: " + StringUtils.progressBar(tag.getInteger("current"), tag.getInteger("max"), 15) + " " + (tag.getInteger("current")/tag.getInteger("max"))*100+"%");
         tooltip.add("-   " + tag.getInteger("current") + "/" + tag.getInteger("max") + " RF");
      }else if(stack.getTagCompound()!=null && stack.getTagCompound().getString("name").equals("Absolute")){
         tooltip.add("Charge: [" + EnumChatFormatting.LIGHT_PURPLE +  "==/==/==/==" + EnumChatFormatting.GRAY + "] " + "101%");
         tooltip.add("-   §k10000§r§7 / §k10000§r§7 RF");
      }
   }

   @Override
   public String getUnlocalizedName() {
      return name.toLowerCase() + "RedstonicBattery";
   }
}
