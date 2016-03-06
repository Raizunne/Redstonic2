package com.raizu.redstonic.Render;

import com.raizu.redstonic.Item.Drill.RedstonicDrill;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;

/**
 * Created by Raizunne as a part of Redstonic2
 * on 06/03/2016, 12:52 AM.
 */
public class RedstonicMeshDefinition implements ItemMeshDefinition {

   private static RedstonicMeshDefinition instance;

   public static RedstonicMeshDefinition instance(){
      if(instance==null){
         instance = new RedstonicMeshDefinition();
      }
      return instance;
   }

   @Override
   public ModelResourceLocation getModelLocation(ItemStack stack) {
      return ((RedstonicDrill)stack.getItem()).getModelLocation(stack);
   }
}
