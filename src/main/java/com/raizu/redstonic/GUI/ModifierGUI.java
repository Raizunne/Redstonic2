package com.raizu.redstonic.GUI;

import com.raizu.redstonic.Item.Drill.RedstonicDrill;
import com.raizu.redstonic.Network.Packet.PacketModifier;
import com.raizu.redstonic.Redstonic;
import com.raizu.redstonic.TileEntity.TERedstonicModifier;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Raizunne as a part of Redstonic2
 * on 06/03/2016, 07:24 PM.
 */
public class ModifierGUI extends GuiContainer {

   TERedstonicModifier modifier;
   GuiTextField rename;
   GuiButton buildButton;
   String renameText;

   public ModifierGUI(InventoryPlayer inventoryPlayer, TERedstonicModifier modifier){
      super(new ModifierContainer(inventoryPlayer, modifier));
      this.modifier = modifier;
      xSize = 176;
      ySize = 171;
      this.renameText = "";
   }

   @Override
   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
      super.drawGuiContainerForegroundLayer(mouseX, mouseY);
      int posX = (width - 176) / 2;
      int posY = (height - 171) / 2;
      fontRendererObj.drawString("Redstonic Modifier", 45, 6, 0x404040);
//      this.rename.drawTextBox();.
      if(modifier.getStackInSlot(0)==null && modifier.getStackInSlot(1)!=null && modifier.getStackInSlot(2)!=null && modifier.getStackInSlot(3)!=null){
         this.buildButton.enabled = true;
         this.buildButton.visible = true;
         this.buildButton.displayString = "Build";
      }else if(modifier.getStackInSlot(1)==null && modifier.getStackInSlot(2)==null && modifier.getStackInSlot(3)==null && modifier.getStackInSlot(0)!=null && modifier.getStackInSlot(0).getItem() instanceof RedstonicDrill){
         this.buildButton.visible = true;
         this.buildButton.enabled = true;
         this.buildButton.displayString = "Deconst.";
      }else{
         this.buildButton.visible = false;
      }
   }

   public static final ResourceLocation texture = new ResourceLocation("redstonic", "textures/gui/RedstonicModifier.png");

   @Override
   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
      RenderHelper.disableStandardItemLighting();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glEnable(GL11.GL_BLEND);
      mc.renderEngine.bindTexture(texture);
      drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
      if(modifier.getStackInSlot(3)==null){
         drawTexturedModalRect(guiLeft+67, guiTop+65, 176, 34, 16, 16);
      }
   }

   @Override
   public void initGui() {
      super.initGui();
      int posX = (width - 176) / 2;
      int posY = (height - 171) / 2;
      buttonList = new ArrayList<GuiButton>();
      this.rename = new GuiTextField(0, fontRendererObj, 91, 20, 67, 11);
      this.rename.setMaxStringLength(35);
      this.rename.setEnableBackgroundDrawing(true);
      this.rename.setText(renameText);
      this.rename.setFocused(true);

      this.buildButton = new GuiButton(1, posX + 10, posY + 65, 40, 14, "Deconst.");
      this.buildButton.enabled = false;

      buttonList.add(buildButton);
   }

   @Override
   protected void actionPerformed(GuiButton button) throws IOException {
      super.actionPerformed(button);
      switch (button.id){
         case 1:
            if(this.buildButton.displayString.equals("Build")){
               modifier.craft(renameText);
               Redstonic.network.sendToServer(new PacketModifier(this.modifier, this.renameText, 0));
            }else if(this.buildButton.displayString.equals("Deconst.")){
               modifier.deconstruct(renameText);
               Redstonic.network.sendToServer(new PacketModifier(this.modifier, this.renameText, 1));
            }
            break;
      }
   }

   @Override
   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
      super.mouseClicked(mouseX, mouseY, mouseButton);
      String name = rename.getText();
      this.rename.mouseClicked(mouseX-guiLeft, mouseY-guiTop, mouseButton);
   }

   @Override
   public void updateScreen() {
      super.updateScreen();
      this.rename.updateCursorCounter();
   }
}
