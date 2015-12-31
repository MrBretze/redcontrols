package com.empiricist.redcontrols.tileentity;

import com.empiricist.redcontrols.init.ModBlocks;
import com.empiricist.redcontrols.utility.LogHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityButtons extends TEBundledEmitter implements ITEBundledLights{
    public int[] signals;

    public TileEntityButtons() {
        super();
        signals = new int[] { 0, 0, 0, 0, 0, 0 ,0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    }

    @Override
    public void updateEntity() {
        if(!worldObj.isRemote) {
            for (int i = 0; i < signals.length; i++) {
                if (signals[i] == 1) {//button runs out
//                    for( ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
//                        worldObj.scheduleBlockUpdate(xCoord+dir.offsetX, yCoord+dir.offsetY, zCoord+dir.offsetZ, ModBlocks.buttons, 10);
//                        LogHelper.info("Schedules update for x:" + (xCoord + dir.offsetX) + " y:" + (yCoord + dir.offsetY) + " z:" + (zCoord + dir.offsetZ));
//                        //worldObj.notifyBlockOfNeighborChange(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, this.blockType);
//                    }
                    //worldObj.notifyBlockChange(xCoord, yCoord, zCoord, this.blockType);
                    //if(!worldObj.isRemote){ LogHelper.info("notify block update"); }
                    //worldObj.scheduleBlockUpdate(xCoord, yCoord, zCoord, ModBlocks.buttons, 2);
                    //worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, ModBlocks.buttons);
                    worldObj.markAndNotifyBlock(xCoord, yCoord, zCoord, worldObj.getChunkFromBlockCoords(xCoord,zCoord), ModBlocks.buttons, ModBlocks.buttons, 3);
                    //ModBlocks.buttons.onBlockActivated(worldObj, xCoord, yCoord, zCoord, null, worldObj.getBlockMetadata(xCoord,yCoord,zCoord), 0, 0, 0);
                }
                if (signals[i] >= 1) {//if time left for signal, decrement counter
                    //if(!worldObj.isRemote){ LogHelper.info("Signal is " + signals[i]); }
                    signals[i]--;
                }
            }
        }
    }

    public byte[] getBundledCableStrength(int blockFace, int toDirection) {
        byte[] result = new byte[16];
        for(int i = 0; i < signals.length; i++){
            result[i] = (byte)(signals[i] > 1 ? -1 : 0); //255 or 0 for unsigned
        }
        //if(!worldObj.isRemote){LogHelper.info("emitting signal to thing at face " + blockFace + " in the block in direction " + toDirection);}
        return result;
    }

    @Override
    public boolean[] getSignalsArray(){
        boolean[] result = new boolean[16];
        for(int i = 0; i < signals.length; i++){
            result[i] = signals[i] > 0;
        }
        return result;
    }

    public void setSignal(int number, int duration){
        if (number < 0 || number >= signals.length){ return; }
        //if(!worldObj.isRemote){ LogHelper.info("Set signal to " + duration); }
        signals[number] = duration;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setIntArray("signals", signals);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        signals = compound.getIntArray("signals");
    }
}
