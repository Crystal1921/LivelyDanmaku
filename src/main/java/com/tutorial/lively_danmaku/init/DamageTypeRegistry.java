package com.tutorial.lively_danmaku.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.data.worldgen.BootstapContext;

import static com.tutorial.lively_danmaku.lively_danmaku.prefix;

public class DamageTypeRegistry {
    public static final ResourceKey<DamageType> DANMAKU_SHOOT =
            ResourceKey.create(Registries.DAMAGE_TYPE, prefix("danmaku_shoot"));

    public static void bootstrap(BootstapContext<DamageType> context) {
        context.register(DANMAKU_SHOOT, new DamageType("danmaku_shoot", 0.0F));
    }
}
