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

    @Override
    public @NotNull Component getLocalizedDeathMessage(LivingEntity living) {
        LivingEntity livingentity = living.getKillCredit();
        String s = "death.attack." + this.type().msgId();
        String s1 = s + ".lively_danmaku";
        if (livingentity != null) {
            for (EntityType<?> entity : entities) {
                if (livingentity.getType() == entity) {
                    return Component.translatable(s, living.getDisplayName());
                }
            }
        }
        return livingentity != null ? Component.translatable(s1, living.getDisplayName(), livingentity.getDisplayName()) : Component.translatable(s, living.getDisplayName());
    }
}
