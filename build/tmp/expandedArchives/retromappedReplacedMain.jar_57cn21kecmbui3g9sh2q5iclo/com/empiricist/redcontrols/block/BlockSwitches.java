package com.empiricist.redcontrols.block;

import com.empiricist.redcontrols.init.ModBlocks;
import com.empiricist.redcontrols.reference.Reference;
import com.empiricist.redcontrols.tileentity.TEBundledEmitter;
import com.empiricist.redcontrols.tileentity.TileEntitySwitches;
import com.empiricist.redcontrols.utility.ChatHelper;
import com.empiricist.redcontrols.utility.LogHelper;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import java.util.List;

public class BlockSwitches extends BlockBundledEmitter {

    //public static int defaultMeta; //so it renders the right side as item
    public static final PropertyDirection FACING = PropertyDirection.func_177712_a("facing", EnumFacing.Plane.HORIZONTAL);
    public static final PropertyInteger VERTICAL = PropertyInteger.func_177719_a("vertical", 0, 2);

    public BlockSwitches(){
        super(Material.field_151576_e);
        this.func_149711_c(3f);
        name = "switchPanel";
        this.func_149663_c(name);
        //defaultMeta = 3;
        //setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(VERTICAL, 1));
    }

    @Override
    public TileEntity func_149915_a(World world, int metadata) {
        return new TileEntitySwitches();
    }


//    //to tell tileentity it is activated
//    @Override
//    public void onNeighborBlockChange(World world, int x, int y, int z, Block block){
//        TileEntity tile = world.getTileEntity(x, y, z);
//        if (tile != null && tile instanceof TileEntityWarpCore) {
//            TileEntityWarpCore warpCore = (TileEntityWarpCore)tile;
//            warpCore.signal = world.isBlockIndirectlyGettingPowered(x, y, z);
//        }
//    }

    @Override
    public boolean func_180639_a(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing face, float clickX, float clickY, float clickZ){
        world.func_175689_h( pos ); // Makes the server call getDescriptionPacket for a full data sync

        if( activeFace(state) != face){ //only does special things if you click on the face with the switches
            return false;
        }

        //if( !world.isRemote ){ ChatHelper.sendText(player, "side: " + face + " f0: " + clickX + " f1: " + clickY + " f2: " + clickZ );}//+ " faceX: " + faceX + " faceY: " + faceY + " button: " + button);}

        if(!world.field_72995_K){
            double faceX = 0;
            double faceY = 0;

            switch ( face.ordinal() ) {
                case 0: //bottom
                    switch( state.func_177229_b(FACING).func_176736_b() ){
                        case 0: //south
                            faceX = clickX;
                            faceY = 1-clickZ;
                            break;
                        case 1: //west
                            faceX = clickZ;
                            faceY = clickX;
                            break;
                        case 2: //north
                            faceX = 1-clickX;
                            faceY = clickZ;
                            break;
                        case 3: //east
                            faceX = 1-clickZ;
                            faceY = 1-clickX;
                            break;
                    }
                    break;
                case 1: //top
                    switch( state.func_177229_b(FACING).func_176736_b() ){
                        case 0: //south
                            faceX = clickX;
                            faceY = clickZ;
                            break;
                        case 1: //west
                            faceX = clickZ;
                            faceY = 1-clickX;
                            break;
                        case 2: //north
                            faceX = 1-clickX;
                            faceY = 1-clickZ;
                            break;
                        case 3: //east
                            faceX = 1-clickZ;
                            faceY = clickX;
                            break;
                    }
                    break;
                case 2: //north
                    faceX = 1 - clickX;
                    faceY = 1 - clickY;
                    break;
                case 3: //south
                    faceX = clickX;
                    faceY = 1 - clickY;
                    break;
                case 4: //west
                    faceX = clickZ;
                    faceY = 1 - clickY;
                    break;
                case 5: //east
                    faceX = 1 - clickZ;
                    faceY = 1 - clickY;
            }

            //which switch did they click?
            double bezel = 2.0/16;
            int button = -1;
            if( (faceX > bezel) && (faceX < (1 - bezel)) && (faceY > bezel) && (faceY < (1 - bezel))){
                int bx = (int)((faceX - bezel)*16/3);
                int by = (int)((faceY - bezel)*16/3);
                //LogHelper.info("bx " + bx + " by " + by + " button " + button + " bezel " + bezel);
                button = bx + 4 * by;
            }

            //tell the tileentity which was clicked
            TileEntity tile = world.func_175625_s( pos );
            if (tile != null && tile instanceof TileEntitySwitches) {
                TileEntitySwitches switchPanel = (TileEntitySwitches)tile;
                switchPanel.setSignal(button, !switchPanel.getSignal(button));
                //Minecraft.getMinecraft().getNetHandler().addToSendQueue(warpCore.getDescriptionPacket());
                //world.markBlockForUpdate(x, y, z);

                //player.openGui(RedControls.instance, 0, world, x, y, z);
            }

            world.func_175685_c(pos, this);
        }

        return true;//something happened
    }

    @Override
    public void func_180633_a(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack stack){
        world.func_180501_a(pos, state.func_177226_a(FACING, getFacingFromEntity(world, pos, entity)).func_177226_a(VERTICAL, getVerticalFromEntity(world, pos, entity)), 2);
    }


    public EnumFacing activeFace(IBlockState state){ //which face are the buttons on for this state?
        switch (state.func_177229_b(VERTICAL)) {
            case 0:
                return EnumFacing.DOWN;
            case 1:
                return state.func_177229_b(FACING);
            case 2:
                return EnumFacing.UP;
        }
        return EnumFacing.UP;
    }

    public static EnumFacing getFacingFromEntity(World worldIn, BlockPos clickedBlock, EntityLivingBase entityIn) {
        return entityIn.func_174811_aO().func_176734_d();
    }

    public static int getVerticalFromEntity(World worldIn, BlockPos clickedBlock, EntityLivingBase entityIn) {
        if (MathHelper.func_76135_e((float)entityIn.field_70165_t - (float)clickedBlock.func_177958_n()) < 2.0F && MathHelper.func_76135_e((float)entityIn.field_70161_v - (float)clickedBlock.func_177952_p()) < 2.0F) //up or down
        {
            double d0 = entityIn.field_70163_u + (double)entityIn.func_70047_e();

            if (d0 - (double)clickedBlock.func_177956_o() > 2.0D)
            {
                return 2;
            }

            if ((double)clickedBlock.func_177956_o() - d0 > 0.0D)
            {
                return 0;
            }
        }
        return 1;
    }

    @Override
    public int func_176201_c(IBlockState state){
        return (state.func_177229_b(VERTICAL) << 2) + state.func_177229_b(FACING).func_176736_b();
    }

    @Override
    public IBlockState func_176203_a(int meta){
        EnumFacing facing = EnumFacing.func_176731_b(meta & 3);//lower 2 bits handle horizontal direction
        int vertical = ((meta >> 2) & 3)%3; //upper 2 bits handle up/flat/down (mod 3 is for safety I guess)
        return func_176223_P().func_177226_a(FACING, facing).func_177226_a(VERTICAL, vertical);
    }

    @Override
    public BlockState func_180661_e() {
        return new BlockState( this, new IProperty[]{VERTICAL, FACING} );
    }

    /*
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list){
        list.add(new ItemStack(item, 1, defaultMeta));
    }

    @Override
    public int damageDropped( int meta ){
        return defaultMeta;
    }

*/
//    //public boolean canRenderInPass(int pass)
//    {
//        return true;
//    }

//    @Override
//    public boolean isOpaqueCube(){
//        return false;
//    }

    /*--
    @Override
    public void onNeighborBlockChange(World world, int xCoord, int yCoord, int zCoord, Block block) {
        super.onNeighborBlockChange(world, xCoord, yCoord, zCoord, block);
        world.setBlockToAir(xCoord, yCoord, zCoord);
    }
    --*/
}
