package com.tutorial.lively_danmaku.entity;

import com.tutorial.lively_danmaku.init.ItemRegistry;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

import static com.tutorial.lively_danmaku.entity.ai.FlyAndShoot.ShootFromEntityToEntity;

public class HakureiDanmaku extends AbstractDanmaku {
    private int time;
    private boolean isPlayerShoot = false;
    private LivingEntity target;
    // 初始速度
    double initialVelocity = 1;
    // 初始位置
    double initialPosition = 1;
    // 阻尼系数
    double dampingFactor = 10;
    public static final EntityDataAccessor<Integer> BULLET_STAGE = SynchedEntityData.defineId(HakureiDanmaku.class, EntityDataSerializers.INT);
    public HakureiDanmaku(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }
    public HakureiDanmaku(EntityType<? extends ThrowableItemProjectile> entityType, Level level, boolean isPlayerShoot) {
        super(entityType, level);
        this.isPlayerShoot = isPlayerShoot;
    }

    public HakureiDanmaku(EntityType<? extends ThrowableItemProjectile> entityType, Level level, int x_rot, int y_rot, int lifetime) {
        super(entityType, level);
        this.setXRot(x_rot);
        this.setYRot(y_rot);
        this.tickCount = lifetime;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegistry.HakureiBulletDanmaku.get();
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
            if (!isPlayerShoot) {
                this.setDeltaMovement(this.getDeltaMovement().multiply(getVelocity(time), getVelocity(time), getVelocity(time)));
            }
        }
        if (tickCount == 40) {
            if (isPlayerShoot) {
                if (!level().isClientSide) {
                    target = getNearestEntity((ServerLevel) this.level(), this, 24);
                }
                if (target != null) {
                    ShootFromEntityToEntity(this, target, this, 0.4F);
                }
            }
            this.getEntityData().set(BULLET_STAGE, 2);
        }
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
    // 获取最近的实体
    public LivingEntity getNearestEntity(ServerLevel world, Entity sourceEntity, double maxDistance) {
        AABB searchRange = sourceEntity.getBoundingBox().inflate(maxDistance);
        List<LivingEntity> entities = world.getEntitiesOfClass(LivingEntity.class, searchRange);
        if (entities.isEmpty()) {
            return null;
        }
        entities.sort(Comparator.comparingDouble(entity -> entity.distanceToSqr(sourceEntity)));
        if (this.getOwner() != null && entities.get(0).is(this.getOwner())){
            return null;
        }
        return entities.get(0);
    }
}
