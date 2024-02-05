package com.tutorial.lively_danmaku.init;

import com.tutorial.lively_danmaku.util.EntityExcludedDamageSource;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

import static com.tutorial.lively_danmaku.LivelyDanmaku.prefix;

public class DamageTypeRegistry {
    public static final ResourceKey<DamageType> DANMAKU_SHOOT =
            ResourceKey.create(Registries.DAMAGE_TYPE, prefix("danmaku_shoot"));

    public static DamageSource getIndirectEntityDamageSource(Level level, ResourceKey<DamageType> type, @Nullable Entity attacker, @Nullable Entity indirectAttacker, EntityType<?>... toIgnore) {
        return toIgnore.length > 0 ? new EntityExcludedDamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(type), toIgnore) : new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(type), attacker, indirectAttacker);
    }
}
