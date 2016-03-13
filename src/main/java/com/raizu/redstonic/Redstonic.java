package com.raizu.redstonic;

import com.raizu.redstonic.Handler.GUIHandler;
import com.raizu.redstonic.Handler.RedstonicEventHandler;
import com.raizu.redstonic.Item.Drill.DrillHead;
import com.raizu.redstonic.Network.CommonProxy;
import com.raizu.redstonic.Network.Packet.PacketModifier;
import com.raizu.redstonic.TileEntity.TERedstonicModifier;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Redstonic.MODID, version = Redstonic.VERSION)
public class Redstonic{
   public static final String MODID = "redstonic";
   public static final String VERSION = "2.0";

   @Mod.Instance
   public static Redstonic instance;
   @SidedProxy(clientSide = "com.raizu.redstonic.Network.ClientProxy", serverSide = "com.raizu.redstonic.Network.CommonProxy")
   public static CommonProxy proxy;
   public static SimpleNetworkWrapper network;

   public static CreativeTabs redTab = new CreativeTabs("Redstonic"){
      @Override
      public Item getTabIconItem() {return Items.apple;}
   };

   @EventHandler
   public void preInit(FMLPreInitializationEvent event){
      network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
      Redstonic.network.registerMessage(PacketModifier.Handler.class, PacketModifier.class, 0, Side.SERVER);

      RedstonicItems.init();
      RedstonicBlocks.init();
      proxy.prepareVariants();

      proxy.registerPreInit();

      MinecraftForge.EVENT_BUS.register(new RedstonicEventHandler());

      GameRegistry.registerTileEntity(TERedstonicModifier.class, "TERedstonicModifier");
   }

   @EventHandler
   public void init(FMLInitializationEvent event){
      proxy.registerRenderers();
      NetworkRegistry.INSTANCE.registerGuiHandler(this, new GUIHandler());
   }
}
