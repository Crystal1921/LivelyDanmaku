package com.tutorial.lively_danmaku.Entity;

import com.tutorial.lively_danmaku.init.DamageTypeRegistry;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

import static com.tutorial.lively_danmaku.data.RegistryDataGenerator.getIndirectEntityDamageSource;

public class Hakurei_bullet extends AbstractDanmaku {
    private final double initialPosition = 1; // 初始位置
    private final double initialVelocity = 1; // 初始速度
    private final double dampingFactor = 10; // 阻尼系数
    private int time;
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

    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        double i = Math.sqrt(entity.distanceToSqr(entity.getDeltaMovement())) / 10;
        if (!(entity instanceof Reimu)) {
            entity.hurt(getIndirectEntityDamageSource(this.level(), DamageTypeRegistry.DANMAKU_SHOOT, this.getOwner(), this), (float)i);
        }
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
