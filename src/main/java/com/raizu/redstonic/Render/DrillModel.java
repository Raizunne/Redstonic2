package com.raizu.redstonic.Render;

import com.google.common.primitives.Ints;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.IFlexibleBakedModel;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.client.model.ISmartItemModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.tuple.Pair;

import javax.vecmath.Matrix4f;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Raizunne as a part of Redstonic2
 * on 06/03/2016, 03:53 PM.
 */
public class DrillModel implements ISmartItemModel, IPerspectiveAwareModel {

   private IBakedModel baseDrillModel;
   private String head, body, power, mod1, mod2, mod3;
   public static final ModelResourceLocation modelResLoc = new ModelResourceLocation("redstonic:RedstonicDrill", "inventory");
   private ItemCameraTransforms.TransformType transformType;

   public DrillModel(IBakedModel model){
      baseDrillModel = model;
   }

   @Override
   public IBakedModel handleItemState(ItemStack stack) {
      if(stack!=null && stack.getTagCompound()!=null){
         NBTTagCompound tag = stack.getTagCompound();
         head = tag.getString("head");
         body = tag.getString("body");
         power = tag.getString("battery");
         mod1 = tag.getString("mod1");
         mod2 = tag.getString("mod2");
         mod3 = tag.getString("mod3");
      }else{
         head = "Iron";
         body = "Iron";
      }
      return this;
   }

   @Override
   public List<BakedQuad> getFaceQuads(EnumFacing p_177551_1_) {
      return baseDrillModel.getFaceQuads(p_177551_1_);
   }

   @Override
   public List<BakedQuad> getGeneralQuads() {
      List<BakedQuad> builtDrill = new ArrayList<BakedQuad>(baseDrillModel.getGeneralQuads());
      builtDrill.addAll(getBuiltDrill(head,body,power,mod1,mod2,mod3));
      return builtDrill;
   }

   @Override
   public boolean isAmbientOcclusion() {
      return baseDrillModel.isAmbientOcclusion();
   }

   @Override
   public boolean isGui3d() {
      return baseDrillModel.isGui3d();
   }

   @Override
   public boolean isBuiltInRenderer() {
      return false;
   }

   @Override
   public TextureAtlasSprite getParticleTexture() {
      return baseDrillModel.getParticleTexture();
   }

   @Override
   public ItemCameraTransforms getItemCameraTransforms() {
      return baseDrillModel.getItemCameraTransforms();
   }

   public List<BakedQuad> getBuiltDrill(final String head, final String body, final String power, final String mod1, final String mod2, final String mod3){
      TextureAtlasSprite headTexture = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("redstonic:items/Drill/Heads/Render/"+head);
      TextureAtlasSprite bodyTexture = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("redstonic:items/Drill/Bodies/Render/"+body);
      BakedQuad[] quads;
      if(transformType!=null) {
         switch (transformType) {
            case GUI:
               quads = new BakedQuad[2];
               quads[0] = createBakedQuadForFace(0.5F, 1, 0.5F, 1, 0, 0, headTexture, EnumFacing.SOUTH);
               quads[1] = createBakedQuadForFace(0.5F, 1, 0.5F, 1, 0, 0, bodyTexture, EnumFacing.SOUTH);
               break;
            case GROUND:
               quads = new BakedQuad[4];
               quads[0] = createBakedQuadForFace(0.5F, 1, 0.5F, 1, -0.5F, 0, headTexture, EnumFacing.SOUTH);
               quads[1] = createBakedQuadForFace(0.5F, 1, 0.5F, 1, -0.5F, 0, bodyTexture, EnumFacing.SOUTH);

               quads[2] = createBakedQuadForFace(0.5F, -1, 0.5F, 1, -0.5F, 0, headTexture, EnumFacing.SOUTH);
               quads[3] = createBakedQuadForFace(0.5F, -1, 0.5F, 1, -0.5F, 0, bodyTexture, EnumFacing.SOUTH);

               break;
            case THIRD_PERSON:
               quads = new BakedQuad[2];
               quads[0] = createBakedQuadForFace(0, -1, 1, -1, 0, 0, headTexture, EnumFacing.NORTH);
               quads[1] = createBakedQuadForFace(0, -1, 1, -1, 0, 0, bodyTexture, EnumFacing.NORTH);
               break;
            case FIXED:
               quads = new BakedQuad[2];
               quads[0] = createBakedQuadForFace(0.5F, 1, 0.5F, 1, -0.5F, 0, headTexture, EnumFacing.SOUTH);
               quads[1] = createBakedQuadForFace(0.5F, 1, 0.5F, 1, -0.5F, 0, bodyTexture, EnumFacing.SOUTH);
               break;
            default:
               quads = new BakedQuad[2];
               quads[0] = createBakedQuadForFace(0, 1, 1, 1, 0, 0, headTexture, EnumFacing.NORTH);
               quads[1] = createBakedQuadForFace(0, 1, 1, 1, 0, 0, bodyTexture, EnumFacing.NORTH);
               break;
         }

      }else {
         quads = new BakedQuad[2];
         quads[0] = createBakedQuadForFace(0, 1, 1, 1, 0, 0, headTexture, EnumFacing.NORTH);
         quads[1] = createBakedQuadForFace(0, 1, 1, 1, 0, 0, bodyTexture, EnumFacing.NORTH);
      }
      return Arrays.asList(quads);
   }

   private BakedQuad createBakedQuadForFace(float centreLR, float width, float centreUD, float height, float forwardDisplacement, int itemRenderLayer, TextureAtlasSprite texture, EnumFacing face){
      float x1, x2, x3, x4;
      float y1, y2, y3, y4;
      float z1, z2, z3, z4;
      final float CUBE_MIN = 0.0F;
      final float CUBE_MAX = 1.0F;

      switch (face) {
         case UP: {
            x1 = x2 = centreLR + width/2.0F;
            x3 = x4 = centreLR - width/2.0F;
            z1 = z4 = centreUD + height/2.0F;
            z2 = z3 = centreUD - height/2.0F;
            y1 = y2 = y3 = y4 = CUBE_MAX + forwardDisplacement;
            break;
         }
         case DOWN: {
            x1 = x2 = centreLR + width/2.0F;
            x3 = x4 = centreLR - width/2.0F;
            z1 = z4 = centreUD - height/2.0F;
            z2 = z3 = centreUD + height/2.0F;
            y1 = y2 = y3 = y4 = CUBE_MIN - forwardDisplacement;
            break;
         }
         case WEST: {
            z1 = z2 = centreLR + width/2.0F;
            z3 = z4 = centreLR - width/2.0F;
            y1 = y4 = centreUD - height/2.0F;
            y2 = y3 = centreUD + height/2.0F;
            x1 = x2 = x3 = x4 = CUBE_MIN - forwardDisplacement;
            break;
         }
         case EAST: {
            z1 = z2 = centreLR - width/2.0F;
            z3 = z4 = centreLR + width/2.0F;
            y1 = y4 = centreUD - height/2.0F;
            y2 = y3 = centreUD + height/2.0F;
            x1 = x2 = x3 = x4 = CUBE_MAX + forwardDisplacement;
            break;
         }
         case NORTH: {
            x1 = x2 = centreLR - width/2.0F;
            x3 = x4 = centreLR + width/2.0F;
            y1 = y4 = centreUD - height/2.0F;
            y2 = y3 = centreUD + height/2.0F;
            z1 = z2 = z3 = z4 = CUBE_MIN - forwardDisplacement;
            break;
         }
         case SOUTH: {
            x1 = x2 = centreLR + width/2.0F;
            x3 = x4 = centreLR - width/2.0F;
            y1 = y4 = centreUD - height/2.0F;
            y2 = y3 = centreUD + height/2.0F;
            z1 = z2 = z3 = z4 = CUBE_MAX + forwardDisplacement;
            break;
         }
         default: {
            assert false : "Unexpected facing in createBakedQuadForFace:" + face;
            return null;
         }
      }

      return new BakedQuad(Ints.concat(vertexToInts(x1, y1, z1, Color.WHITE.getRGB(), texture, 16, 16),
           vertexToInts(x2, y2, z2, Color.WHITE.getRGB(), texture, 16, 0),
           vertexToInts(x3, y3, z3, Color.WHITE.getRGB(), texture, 0, 0),
           vertexToInts(x4, y4, z4, Color.WHITE.getRGB(), texture, 0, 16)),
           itemRenderLayer, face);
   }


   private int[] vertexToInts(float x, float y, float z, int color, TextureAtlasSprite texture, float u, float v){
      return new int[] {
           Float.floatToRawIntBits(x),
           Float.floatToRawIntBits(y),
           Float.floatToRawIntBits(z),
           color,
           Float.floatToRawIntBits(texture.getInterpolatedU(u)),
           Float.floatToRawIntBits(texture.getInterpolatedV(v)),
           0
      };
   }

   @Override
   public Pair<? extends IFlexibleBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
      this.transformType = cameraTransformType;
      switch(cameraTransformType){
         case FIRST_PERSON:
            return Pair.of(this, ForgeHooksClient.getMatrix(this.baseDrillModel.getItemCameraTransforms().firstPerson));
         case THIRD_PERSON:
            return Pair.of(this, ForgeHooksClient.getMatrix(this.baseDrillModel.getItemCameraTransforms().thirdPerson));
         case GUI:
            return Pair.of(this, ForgeHooksClient.getMatrix(this.baseDrillModel.getItemCameraTransforms().gui));
         case GROUND:
            return Pair.of(this, ForgeHooksClient.getMatrix(this.baseDrillModel.getItemCameraTransforms().ground));
         case HEAD:
            return Pair.of(this, ForgeHooksClient.getMatrix(this.baseDrillModel.getItemCameraTransforms().head));
         case FIXED:
            return Pair.of(this, ForgeHooksClient.getMatrix(this.baseDrillModel.getItemCameraTransforms().fixed));
         default: return null;
      }
   }

   @Override
   public VertexFormat getFormat() {
      return null;
   }
}
