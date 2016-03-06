package com.raizu.redstonic;

import com.raizu.redstonic.Item.Drill.DrillHead;
import com.raizu.redstonic.Network.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Redstonic.MODID, version = Redstonic.VERSION)
public class Redstonic{
   public static final String MODID = "redstonic";
   public static final String VERSION = "2.0";

   @SidedProxy(clientSide = "com.raizu.redstonic.Network.ClientProxy", serverSide = "com.raizu.redstonic.Network.CommonProxy")
   public static CommonProxy proxy;

   public static CreativeTabs redTab = new CreativeTabs("Redstonic"){
      @Override
      public Item getTabIconItem() {return Items.apple;}
   };

   @EventHandler
   public void preInit(FMLPreInitializationEvent event){
      RedstonicItems.init();
      proxy.prepareVariants();
   }

    @EventHandler
    public void init(FMLInitializationEvent event){
       proxy.registerRenderers();
    }
}
