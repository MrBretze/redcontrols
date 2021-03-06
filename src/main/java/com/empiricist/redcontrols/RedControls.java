package com.empiricist.redcontrols;

import com.empiricist.redcontrols.client.gui.GuiHandler;
import com.empiricist.redcontrols.handler.ConfigurationHandler;
import com.empiricist.redcontrols.init.Integration;
import com.empiricist.redcontrols.init.ModBlocks;
import com.empiricist.redcontrols.init.ModItems;
import com.empiricist.redcontrols.init.Recipes;
import com.empiricist.redcontrols.proxy.IProxy;
import com.empiricist.redcontrols.reference.Reference;
import com.empiricist.redcontrols.utility.LogHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY_CLASS)
public class RedControls {
    @Mod.Instance(Reference.MOD_ID)
    public static RedControls instance;//instance of mod

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static IProxy proxy;//holds proxy (ClientProxy on client, ServerProxy on server)

    //Preinit - config, network, items, blocks
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        //handle config file at default location
        ConfigurationHandler.init(event.getSuggestedConfigurationFile());

        //register our class to listen for config events from event bus
        MinecraftForge.EVENT_BUS.register(new ConfigurationHandler());

        //register keybindings
        //proxy.registerKeyBindings(); //no key bindings needed

        //initialize the mod's items and blocks
        ModItems.init();
        ModBlocks.init();

        LogHelper.info("PreInit Complete");
    }

    //init - gui handler, tileentity, renderers, event handlers, recipes
    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        //register other classes to listen for events from event bus
        //FMLCommonHandler.instance().bus().register(new KeyInputEventHandler());

        //register recipes
        Recipes.init();

        //new GuiHandler();

        //renderers
        proxy.registerModels();
        proxy.registerTESR();

        LogHelper.info("Init Complete");
    }

    //postinit - wrap up after mods initialize (ie compatibility)
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){
        Integration.postInit();
        LogHelper.info("PostInit Complete");

    }
}
