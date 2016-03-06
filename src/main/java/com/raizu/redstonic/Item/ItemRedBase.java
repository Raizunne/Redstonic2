package com.raizu.redstonic.Item;

import com.raizu.redstonic.Redstonic;
import com.raizu.redstonic.RedstonicItems;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raizunne as a part of Redstonic2
 * on 06/03/2016, 12:54 AM.
 */
public class ItemRedBase extends Item {

   public ItemRedBase(){
      setCreativeTab(Redstonic.redTab);
   }

   @SideOnly(Side.CLIENT)
   public ResourceLocation[] getItemVariants(){
      return new ResourceLocation[]{Item.itemRegistry.getNameForObject(this)};
   }

   @SideOnly(Side.CLIENT)
   public ModelResourceLocation getModelLocation(ItemStack stack){
      return null;
   }
}
