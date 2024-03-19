package com.tutorial.lively_danmaku.capability;

import net.minecraft.nbt.CompoundTag;

public class FlyCapability {
    private boolean isFly;
    public boolean getFly() {
        return this.isFly;
    }
    public void setFly(boolean isFly) {
        this.isFly = isFly;
    }

    public void copyFrom(FlyCapability source){
        this.isFly = source.getFly();
    }

    public void saveNBTData(CompoundTag nbt){
        nbt.putBoolean("isFly",this.isFly);
    }
    public void loadNBTData(CompoundTag nbt){
        this.isFly = nbt.getBoolean("isFly");
    }
}
