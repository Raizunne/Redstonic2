package com.raizu.redstonic.Item;

import net.minecraft.item.Item;

/**
 * Created by Raizunne as a part of Redstonic2
 * on 06/03/2016, 06:20 PM.
 */
public class Augment extends Item {

   public String name;

   public Augment(String name){
      this.name = name;
      this.setRegistryName(getUnlocalizedName());
      this.setUnlocalizedName(getUnlocalizedName());
   }

   @Override
   public String getUnlocalizedName() {
      return name.toLowerCase()+"Augment";
   }
}
