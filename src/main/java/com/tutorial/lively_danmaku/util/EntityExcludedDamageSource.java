package com.tutorial.lively_danmaku.util;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class EntityExcludedDamageSource extends DamageSource {

    protected final List<EntityType<?>> entities;

    public EntityExcludedDamageSource(Holder<DamageType> type, EntityType<?>... entities) {
        super(type);
        this.entities = Arrays.stream(entities).toList();
    }
}
