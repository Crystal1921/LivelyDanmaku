package com.tutorial.lively_danmaku.Entity;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class Hakurei_bullet extends AbstractDanmaku {
    private int time;
    // 初始速度
    double initialVelocity = 1;
    // 初始位置
    double initialPosition = 1;
    // 阻尼系数
    double dampingFactor = 10;
    public static final EntityDataAccessor<Integer> BULLET_STAGE = SynchedEntityData.defineId(Hakurei_bullet.class, EntityDataSerializers.INT);
    public Hakurei_bullet(EntityType<? extends ThrowableItemProjectile> p_37442_, Level p_37443_) {
        super(p_37442_, p_37443_);
    }

    public Hakurei_bullet(EntityType<? extends ThrowableItemProjectile> p_37442_, Level p_37443_,int x_rot, int y_rot, int lifetime) {
        super(p_37442_, p_37443_);
        this.setXRot(x_rot);
        this.setYRot(y_rot);
        this.tickCount = lifetime;
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }

    protected void onHit(@NotNull HitResult hitResult) {
        super.onHit(hitResult);
        if (!level().isClientSide) {
            level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(BULLET_STAGE, 1);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getEntityData().get(BULLET_STAGE) == 1) {
            time++;
            this.setDeltaMovement(this.getDeltaMovement().multiply(getVelocity(time),getVelocity(time),getVelocity(time)));
        }
        if (tickCount == 50) this.getEntityData().set(BULLET_STAGE,2);
        if (tickCount >= 200) {
            this.discard();
        }
    }
    public double getVelocity(double time) {
        double exponentialTerm = Math.exp(-dampingFactor * time);
        return  1 / (initialVelocity * exponentialTerm +
                (initialPosition * dampingFactor + initialVelocity) *
                        (1 - exponentialTerm) / dampingFactor);
    }
}
