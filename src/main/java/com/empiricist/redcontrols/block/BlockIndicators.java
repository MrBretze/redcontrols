package com.empiricist.redcontrols.block;

import com.empiricist.redcontrols.reference.Reference;
import com.empiricist.redcontrols.tileentity.TileEntityButtons;
import com.empiricist.redcontrols.tileentity.TileEntityIndicators;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class BlockIndicators extends BlockBundledReceiver{

    public static int defaultMeta; //so it renders the right side as item

    public BlockIndicators(){
        super(Material.rock);
        this.setBlockName("indicatorPanel");
        defaultMeta = 3;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityIndicators();
    }


    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack){
        boolean closeH = (MathHelper.abs((float) entity.posX - (float) x) < 2.0F && MathHelper.abs((float)entity.posZ - (float)z) < 2.0F);

        double d0 = entity.posY + 1.82D - (double) entity.yOffset;

        if (closeH && (d0 - (double) y > 2.0D)) {
            world.setBlockMetadataWithNotify(x, y, z, 1, 2);
            return;
        } else if (closeH && ((double) y - d0 > 0.0D)) {
            world.setBlockMetadataWithNotify(x, y, z, 0, 2);
            return;
        }


        int l = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        int m = (l == 0 ? 2 : (l == 1 ? 5 : (l == 2 ? 3 : (l == 3 ? 4 : 0))));
        world.setBlockMetadataWithNotify(x, y, z, m, 2);

    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list){
        list.add(new ItemStack(item, 1, defaultMeta));
    }

    @Override
    public int damageDropped( int meta ){
        return defaultMeta;
    }



    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta){
        //top and bottom have different texture
        return (side == meta ) ? this.blockIcon : Blocks.stone_slab.getIcon(0,0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg)
    {
        //super.registerBlockIcons(reg);
        //LogHelper.warn(Reference.MOD_ID + ":panel");
        this.blockIcon = reg.registerIcon(Reference.MOD_ID + ":panel");
    }

//    //public boolean canRenderInPass(int pass)
//    {
//        return true;
//    }

    @Override
    public boolean isOpaqueCube(){
        return false;
    }
}
