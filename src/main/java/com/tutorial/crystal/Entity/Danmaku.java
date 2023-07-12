package com.tutorial.crystal.Entity;

import com.tutorial.crystal.init.ItemRegistry;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class Danmaku extends AbstractDanmaku {
    private static final EntityDataAccessor<Float> SIZE = SynchedEntityData.defineId(Danmaku.class, EntityDataSerializers.FLOAT);
    public Danmaku(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public Danmaku(EntityType<? extends ThrowableItemProjectile> type, Level level, float size) {
        super(type, level);
        this.getEntityData().set(SIZE,size);
    }

    protected void onHit(@NotNull HitResult hitResult) {
        super.onHit(hitResult);
        if (!level().isClientSide) {
            level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }
    @Override
    public void tick() {
        super.tick();
        if (tickCount >= 100) {
            this.discard();
        }
    }
    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegistry.ItemDanmaku.get();
    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(SIZE, 0.5F);
    }
    public float getSize() {return this.getEntityData().get(SIZE);}
}
