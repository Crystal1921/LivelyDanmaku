package com.tutorial.lively_danmaku.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;

public class PowerCapability {
    private float power;
    private boolean isChanged;
    private final float MAX_POWER = 5F;
    public float getPower() {
        return this.power;
    }
    public void addPower(float add) {
        this.power += add;
        markChanged();
    }
    public void setPower(float power) {
        this.power = Mth.clamp(power, 0, MAX_POWER);
        markChanged();
    }

    private void markChanged() {
        this.isChanged = true;
    }

    public void setChanged(boolean isChanged) {
        this.isChanged = isChanged;
    }

    public boolean isChanged() {
        return isChanged;
    }

    public void copyFrom(PowerCapability source){
        this.power = source.getPower();
        markChanged();
    }

    public void saveNBTData(CompoundTag nbt){
        nbt.putFloat("power",this.power);
    }
    public void loadNBTData(CompoundTag nbt){
        this.power = nbt.getFloat("power");
    }
}
