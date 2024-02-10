package com.tutorial.lively_danmaku.entity;

import com.tutorial.lively_danmaku.init.ItemRegistry;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class NormalDanmaku extends AbstractDanmaku {
    private static final EntityDataAccessor<Float> SIZE = SynchedEntityData.defineId(NormalDanmaku.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(NormalDanmaku.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_TICK = SynchedEntityData.defineId(NormalDanmaku.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> POSITION_X = SynchedEntityData.defineId(NormalDanmaku.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> POSITION_Y = SynchedEntityData.defineId(NormalDanmaku.class, EntityDataSerializers.INT);
    private int tickTime;
    public NormalDanmaku(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        this(entityType, level,0.5F);
    }

    public NormalDanmaku(EntityType<? extends ThrowableItemProjectile> type, Level level, float size) {
        this(type, level, size, Color.RED);
    }

    public NormalDanmaku(EntityType<? extends ThrowableItemProjectile> type, Level level, float size, Color color) {
        super(type,level);
        this.getEntityData().set(SIZE,size);
        this.getEntityData().set(IS_TICK,true);
        this.getEntityData().set(COLOR, color.getRGB());
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
        if (this.getEntityData().get(IS_TICK)) this.tickTime++;
        if (tickTime >= 100) this.discard();
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegistry.ItemDanmaku.get();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(SIZE, 0.5F);
        this.getEntityData().define(IS_TICK, true);
        this.getEntityData().define(COLOR, Color.red.getRGB());
        this.getEntityData().define(POSITION_X,0);
        this.getEntityData().define(POSITION_Y,0);
    }

    public float getSize() {
        return this.getEntityData().get(SIZE);
    }

    public int getColor() {
        return this.getEntityData().get(COLOR);
    }

    @Override
    public void setIsTick(boolean isTick) {
        this.getEntityData().set(IS_TICK,isTick);
    }
}
