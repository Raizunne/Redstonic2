package com.raizu.redstonic.Item.Drill;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by Raizunne as a part of Redstonic2
 * on 03/03/2016, 07:12 PM.
 */
public class DrillHead extends Item {

   Head head;

   public DrillHead(Head head){
      this.head = head;
      setMaxStackSize(1);
      setUnlocalizedName(getUnlocalizedName());
   }

   @Override
   public String getUnlocalizedName() {
      return head.getName();
   }

   public enum Head{

      IRON("Iron", 1);

      private String name;
      private float speed;
      Head(String name, float speed){

      }

      String getName(){
         return name;
      }

      float getSpeed(){
         return speed;
      }
   }
}