package com.tutorial.lively_danmaku.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class CapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<FlyCapability> FLY = CapabilityManager.get(new CapabilityToken<>() {});
    private FlyCapability flyCapability = null;
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if (capability == FLY) {
            return LazyOptional.of(this::createCapability).cast();
        }
        return LazyOptional.empty();
    }

    @Nonnull
    private FlyCapability createCapability() {
        if (flyCapability == null) {
            this.flyCapability = new FlyCapability();
        }
        return flyCapability;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createCapability().loadNBTData(nbt);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createCapability().saveNBTData(nbt);
        return nbt;
    }
}
