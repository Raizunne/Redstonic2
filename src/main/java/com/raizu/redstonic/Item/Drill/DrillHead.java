package com.raizu.redstonic.Item.Drill;

import com.raizu.redstonic.Redstonic;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Raizunne as a part of Redstonic2
 * on 03/03/2016, 07:12 PM.
 */
public class DrillHead extends Item {

   public float speed;
   public String name;
   public String texture;

   public DrillHead(String name, float speed) {
      super();
      this.name = name;
      this.speed = speed;
      this.setMaxStackSize(1);
      this.setCreativeTab(Redstonic.redTab);
      this.setUnlocalizedName(getUnlocalizedName());
      this.setRegistryName(name.toLowerCase() + "DrillHead");
      this.texture = Redstonic.MODID + ":" + getUnlocalizedName();
   }

   public DrillHead(String name, float speed, String texture){
      super();
      this.name = name;
      this.speed = speed;
      this.setMaxStackSize(1);
      this.setCreativeTab(Redstonic.redTab);
      this.setUnlocalizedName(getUnlocalizedName());
      this.setRegistryName(name.toLowerCase() + "DrillHead");
      this.texture = texture;
   }

   @Override
   public String getUnlocalizedName() {
      return name.toLowerCase()+"DrillHead";
   }

}