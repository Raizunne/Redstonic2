package com.raizu.redstonic.Util;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

/**
 * Created by Raizunne as a part of Redstonic2
 * on 06/03/2016, 02:17 AM.
 */
public class StringUtils {

   public static String progressBar(int cap, int max, int size){
      String progress = "["+ EnumChatFormatting.GREEN;
      int curr = (cap*size)/max;
      for(int i=0; i<curr; i++){
         progress += "=";
      }
      progress+=EnumChatFormatting.GRAY;
      for(int i=0; i<size-curr; i++){
         progress += " ";
      }
      progress +="]";
      return progress;
   }

}
