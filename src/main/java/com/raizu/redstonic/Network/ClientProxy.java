package com.raizu.redstonic.Network;

import com.raizu.redstonic.Item.Drill.RedstonicDrill;
import com.raizu.redstonic.RedstonicItems;
import net.minecraft.client.renderer.block.model.ModelBlockDefinition;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Raizunne as a part of Redstonic2
 * on 05/03/2016, 06:49 PM.
 */
public class ClientProxy extends CommonProxy{

   public void prepareVariants(){
      RedstonicItems.registerDrill((RedstonicDrill)RedstonicItems.redstonicDrill);
   }

   public void registerRenderers(){
      RedstonicItems.registerRenderers();
   }

}
