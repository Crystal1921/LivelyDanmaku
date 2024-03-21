package com.tutorial.lively_danmaku.capability;

import net.minecraft.nbt.CompoundTag;

public class PowerCapability {
    private float power;
    public float getPower() {
        return this.power;
    }
    public void addPower(float add) {this.power += add;}
    public void setPower(float power) {
        this.power = power;
    }

    public void copyFrom(PowerCapability source){
        this.power = source.getPower();
    }

    public void saveNBTData(CompoundTag nbt){
        nbt.putFloat("power",this.power);
    }
    public void loadNBTData(CompoundTag nbt){
        this.power = nbt.getFloat("power");
    }
}
