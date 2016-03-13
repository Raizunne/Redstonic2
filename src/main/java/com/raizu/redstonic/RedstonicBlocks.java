package com.raizu.redstonic;

import com.raizu.redstonic.Block.RedstonicModifier;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Raizunne as a part of Redstonic2
 * on 03/03/2016, 07:10 PM.
 */
public class RedstonicBlocks {

   public static RedstonicModifier modifier;

   public static void init(){
      modifier = new RedstonicModifier();
      GameRegistry.registerBlock(modifier);
   }

}
