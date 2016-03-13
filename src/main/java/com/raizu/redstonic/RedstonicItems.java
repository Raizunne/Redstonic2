package com.raizu.redstonic;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.raizu.redstonic.Item.Augment;
import com.raizu.redstonic.Item.Drill.DrillBody;
import com.raizu.redstonic.Item.Drill.DrillHead;
import com.raizu.redstonic.Item.PowerBattery;
import com.raizu.redstonic.Item.Drill.RedstonicDrill;
import com.raizu.redstonic.Render.ModelBakeEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * Created by Raizunne as a part of Redstonic2
 * on 03/03/2016, 07:10 PM.
 */
public class RedstonicItems {

   public static HashMap<String, DrillHead> drills = new HashMap<String, DrillHead>();
   public static HashMap<String, DrillBody> bodies = new HashMap<String, DrillBody>();
   public static HashMap<String, PowerBattery> power = new HashMap<String, PowerBattery>();
   public static HashMap<String, Item> drillMods = new HashMap<String, Item>();
   public static Item redstonicDrill;

   public static void init(){
      redstonicDrill = new RedstonicDrill();
      GameRegistry.registerItem(redstonicDrill);
      JsonParser parser = new JsonParser();
      try {
         String jsonDrills = new String(Files.readAllBytes(Paths.get(RedstonicItems.class.getResource("Item/Drill/Drills.json").toURI())));
         JsonObject obj = (JsonObject)parser.parse(jsonDrills);
         JsonArray arr = (JsonArray)obj.get("heads");
         for(int i=0; i<arr.size(); i++){
            JsonObject newHead = (JsonObject)arr.get(i);
            DrillHead newDrillHead = new DrillHead(newHead.get("name").getAsString(), Float.valueOf(newHead.get("speed").getAsString()));
            drills.put(newDrillHead.name, newDrillHead);
            GameRegistry.registerItem(newDrillHead);
         }
         JsonArray bods = (JsonArray)obj.get("bodies");
         for(int i=0; i<bods.size(); i++){
            JsonObject newBody = (JsonObject)bods.get(i);
            DrillBody newDrillBody = new DrillBody(newBody.get("name").getAsString(), Integer.valueOf(newBody.get("mods").getAsString()));
            bodies.put(newDrillBody.name, newDrillBody);
            GameRegistry.registerItem(newDrillBody);
         }
         JsonArray pows = (JsonArray)obj.get("power");
         for(int i=0; i<pows.size(); i++){
            JsonObject newPower = (JsonObject)pows.get(i);
            PowerBattery newDrillPower = new PowerBattery(newPower.get("name").getAsString(), Integer.valueOf(newPower.get("max").getAsString()), Integer.valueOf(newPower.get("maxReceive").getAsString()));
            power.put(newDrillPower.name, newDrillPower);
            GameRegistry.registerItem(newDrillPower);
         }
         JsonArray drillAugs = (JsonArray)obj.get("augments");
         for(int i=0; i<drillAugs.size(); i++){
            JsonObject newAug = (JsonObject)drillAugs.get(i);
            Augment newDrillAug = new Augment(newAug.get("name").getAsString());
            drillMods.put(newDrillAug.name, newDrillAug);
            GameRegistry.registerItem(newDrillAug);
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public static void registerRenderers(){
      for(Item item : drills.values()){
         registerRender(item);
      }
      for(Item item : bodies.values()){
         registerRender(item);
      }
      for(Item item : power.values()){
         registerRender(item);
      }
      for(Item item : drillMods.values()){
         registerRender(item);
      }
      registerRender(redstonicDrill);
   }

   public static void registerRender(Item item){
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Redstonic.MODID + ":" + item.getUnlocalizedName(), "inventory"));
   }

}
