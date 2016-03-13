package com.raizu.redstonic.Network;

import com.raizu.redstonic.Item.Drill.RedstonicDrill;
import com.raizu.redstonic.RedstonicItems;
import com.raizu.redstonic.Render.ModelBakeEventHandler;
import net.minecraft.client.renderer.block.model.ModelBlockDefinition;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Raizunne as a part of Redstonic2
 * on 05/03/2016, 06:49 PM.
 */
public class ClientProxy extends CommonProxy{

   public void registerRenderers(){
      RedstonicItems.registerRenderers();
   }

   public void registerPreInit(){
      MinecraftForge.EVENT_BUS.register(ModelBakeEventHandler.instance);
      Item redDrill = GameRegistry.findItem("redstonic", "RedstonicDrill");
      ModelResourceLocation modRes = new ModelResourceLocation("redstonic:RedstonicDrill", "inventory");
      ModelLoader.setCustomModelResourceLocation(redDrill, 0, modRes);
   }
}
