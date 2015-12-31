package com.empiricist.redcontrols.init;

import com.empiricist.redcontrols.item.*;
import com.empiricist.redcontrols.reference.Reference;
import cpw.mods.fml.common.registry.GameRegistry;

//this annotation tells forge to preserve this as reference w/o modification (unnecessary, good practice)
@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems {
    public static final ItemBase debugger = new ItemDebugger();
    public static final ItemBase itemBearingCompass = new ItemBearingCompass();
    public static final ItemPowerWand powerWand = new ItemPowerWand();

    //register items from mod
    public static void init(){
        GameRegistry.registerItem(debugger, "debugger");
        GameRegistry.registerItem(itemBearingCompass, "itemBearingCompass");
        GameRegistry.registerItem(powerWand, "powerWand");
    }
}
