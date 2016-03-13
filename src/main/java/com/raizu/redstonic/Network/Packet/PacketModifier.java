package com.raizu.redstonic.Network.Packet;

import com.raizu.redstonic.TileEntity.TERedstonicModifier;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by Raizunne as a part of Redstonic2
 * on 06/03/2016, 09:08 PM.
 */
public class PacketModifier implements IMessage {

   private int x, y, z, mode;
   private String rename;

   public PacketModifier(){

   }

   public PacketModifier(TERedstonicModifier te, String rename, int mode){
      this.rename = rename;
      this.x = te.getPos().getX();
      this.y = te.getPos().getY();
      this.z = te.getPos().getZ();
      this.mode = mode;
   }

   @Override
   public void fromBytes(ByteBuf buf) {
      x = buf.readInt();
      y = buf.readInt();
      z = buf.readInt();
      rename = ByteBufUtils.readUTF8String(buf);
      mode = buf.readInt();
   }

   @Override
   public void toBytes(ByteBuf buf) {
      buf.writeInt(x);
      buf.writeInt(y);
      buf.writeInt(z);
      ByteBufUtils.writeUTF8String(buf, rename);
      buf.writeInt(mode);
   }

   public static class Handler implements IMessageHandler<PacketModifier, IMessage>{
      @Override
      public IMessage onMessage(PacketModifier message, MessageContext ctx) {
         TileEntity tile = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(new BlockPos(message.x, message.y, message.z));
         if(tile instanceof TERedstonicModifier){
            TERedstonicModifier modf = (TERedstonicModifier)tile;
            switch(message.mode){
               case 0: modf.craft(message.rename); break;
               case 1: modf.deconstruct(message.rename); break;
            }
            ctx.getServerHandler().playerEntity.worldObj.markBlockForUpdate(new BlockPos(message.x, message.y, message.z));
         }
         return null;
      }
   }

}
