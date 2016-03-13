package com.raizu.redstonic.Handler;

import com.raizu.redstonic.Item.Drill.DrillBody;
import com.raizu.redstonic.Item.Drill.DrillHead;
import com.raizu.redstonic.RedstonicItems;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by Raizunne as a part of Redstonic2
 * on 11/03/2016, 11:36 PM.
 */
public class RedstonicEventHandler {

   @SubscribeEvent
   public void stitcherEvent(TextureStitchEvent.Pre event){
      for(DrillHead head : RedstonicItems.drills.values()){
         event.map.registerSprite(new ResourceLocation("redstonic:items/Drill/Heads/Render/" + head.name));
      }
      for(DrillBody head : RedstonicItems.bodies.values()){
         event.map.registerSprite(new ResourceLocation("redstonic:items/Drill/Bodies/Render/"+head.name));
      }
   }

}
