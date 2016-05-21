package com.empiricist.redcontrols.init;

import com.empiricist.redcontrols.block.BlockButtons;
import com.empiricist.redcontrols.block.BlockIndicators;
import com.empiricist.redcontrols.block.BlockSwitches;
import com.empiricist.redcontrols.block.BlockText;
import com.empiricist.redcontrols.handler.ConfigurationHandler;
import com.empiricist.redcontrols.utility.LogHelper;
import com.empiricist.redcontrols.init.ModItems;
import com.google.common.collect.Multimap;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

public class Recipes {

    //register recipes
    public static void init(){
        //to add vanilla shaped crafting recipe (can also use addShapedRecipe?)

        //button panel
        GameRegistry.addRecipe(new ItemStack(ModBlocks.buttons, 1, 0), "sss", "bbb", "sss", 's', new ItemStack(Blocks.stone_slab), 'b', new ItemStack(Blocks.stone_button));
        GameRegistry.addRecipe(new ItemStack(ModBlocks.buttons, 1, 0), "sss", "bbb", "sss", 's', new ItemStack(Blocks.stone_slab), 'b', new ItemStack(Blocks.wooden_button));

        //switch panel
        GameRegistry.addRecipe(new ItemStack(ModBlocks.switches, 1, 0), "sss", "lll", "sss", 's', new ItemStack(Blocks.stone_slab), 'l', new ItemStack(Blocks.lever));
        GameRegistry.addRecipe(new ItemStack(ModBlocks.switches, 1, 0), "sss", "ttt", "sss", 's', new ItemStack(Blocks.stone_slab), 't', new ItemStack(ModBlocks.toggleSwitch));

        //indicator panel
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.indicators, 1, 0), "sss", "ggg", "sss", 's', new ItemStack(Blocks.stone_slab), 'g', "blockGlass"));

        //character panel
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.charPanel, 1, 0), "sss", "gng", "sss", 's', new ItemStack(Blocks.stone_slab), 'g', "blockGlass", 'n', Items.sign));

        //toggle switch
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.toggleSwitch), new ItemStack(Blocks.lever), new ItemStack(Blocks.stone_button));
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.toggleSwitch), new ItemStack(Blocks.lever), new ItemStack(Blocks.wooden_button));

        //analog emitter
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.analogPower), "sss", "rtr", "sss", 's', new ItemStack(Blocks.stone_slab,1,3), 't', new ItemStack(Blocks.redstone_torch), 'r', "dustRedstone"));

        //power wand
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powerWand), new ItemStack(Blocks.redstone_torch), "stickWood", "dustRedstone"));

        //breaker switch
        GameRegistry.addRecipe(new ItemStack(ModBlocks.breakerSwitch, 1, 0), "ll", 'l', new ItemStack(Blocks.lever));

        //cover button
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModBlocks.coverButton), "blockGlass", new ItemStack(Blocks.stone_button)));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModBlocks.coverButton), "blockGlass", new ItemStack(Blocks.wooden_button)));

        //big lever
        GameRegistry.addRecipe(new ItemStack(ModBlocks.bigLever, 1, 0), "s", "l", 's', Items.stick, 'l', new ItemStack(Blocks.lever));

        //pressure panels
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.woodPanel), new ItemStack(Blocks.wooden_pressure_plate));
        GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wooden_pressure_plate), new ItemStack(ModBlocks.woodPanel));
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.stonePanel), new ItemStack(Blocks.stone_pressure_plate));
        GameRegistry.addShapelessRecipe(new ItemStack(Blocks.stone_pressure_plate), new ItemStack(ModBlocks.stonePanel));
        GameRegistry.addRecipe(new ItemStack(ModBlocks.playerPanel), "bb", 'b', new ItemStack(Blocks.stonebrick));
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.ironPanels[0]), new ItemStack(Blocks.heavy_weighted_pressure_plate));
        GameRegistry.addShapelessRecipe(new ItemStack(Blocks.heavy_weighted_pressure_plate), new ItemStack(ModBlocks.ironPanels[0]));
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.goldPanels[0]), new ItemStack(Blocks.light_weighted_pressure_plate));
        GameRegistry.addShapelessRecipe(new ItemStack(Blocks.light_weighted_pressure_plate), new ItemStack(ModBlocks.goldPanels[0]));

        //dac
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.dac), "sss", "rtr", "sss", 's', new ItemStack(Blocks.stone_slab), 't', new ItemStack(Blocks.redstone_torch), 'r', "dustRedstone"));

    }

    public static void postInit(){

    }

    public static boolean exists(String modid, String name) {
        if ( GameRegistry.findItem(modid, name) != null) {
            LogHelper.info("Found item " + name + " for recipe");
            return true;
        }else if (GameRegistry.findBlock(modid, name) != null){
            LogHelper.info("Found block " + name + " for recipe");
            return true;
        }else{
            LogHelper.warn("Did not find " + name + " for recipe!");
            return false;
        }
    }
}
