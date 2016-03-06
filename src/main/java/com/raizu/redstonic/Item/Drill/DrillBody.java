package com.raizu.redstonic.Item.Drill;

import com.raizu.redstonic.Redstonic;
import net.minecraft.item.Item;

/**
 * Created by Raizunne as a part of Redstonic2
 * on 03/03/2016, 07:12 PM.
 */
public class DrillBody extends Item {

   public String name;
   public int modifiers;

   public DrillBody(String name, int modifiers) {
      super();
      this.name = name;
      this.modifiers = modifiers;
      this.setCreativeTab(Redstonic.redTab);
      this.setMaxStackSize(1);
      this.setUnlocalizedName(getUnlocalizedName());
      this.setRegistryName(name.toLowerCase() + "DrillBody");
   }

   @Override
   public String getUnlocalizedName() {
      return name.toLowerCase() + "DrillBody";
   }
}
