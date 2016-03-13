package com.raizu.redstonic.Render;

import net.minecraft.client.resources.model.IBakedModel;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by Raizunne as a part of Redstonic2
 * on 11/03/2016, 09:59 PM.
 */
public class ModelBakeEventHandler {

   public static final ModelBakeEventHandler instance = new ModelBakeEventHandler();
   private ModelBakeEventHandler(){}

   @SubscribeEvent
   public void onModelBakeEvent(ModelBakeEvent event){
      Object object = event.modelRegistry.getObject(DrillModel.modelResLoc);
      if(object!=null && object instanceof IBakedModel){
         IBakedModel exModel = (IBakedModel)object;
         DrillModel drillModel = new DrillModel(exModel);
         event.modelRegistry.putObject(DrillModel.modelResLoc, drillModel);
      }
   }

}
