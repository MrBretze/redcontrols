package com.empiricist.redcontrols.tileentity;

import com.empiricist.redcontrols.init.ModBlocks;

public class TileEntityDAC extends TEBundledReceiver{

    @Override
    public void onBundledInputChanged() {
        super.onBundledInputChanged();
        field_145850_b.func_175685_c(field_174879_c, ModBlocks.dac);
    }

    public int getRedstoneStrength(){
        int sum = 0;
        for(int i = 0; i < 4; i++){
            if(signals[i] != 0){
                sum += (1<<i);
            }
        }
        return sum;
    }

}
