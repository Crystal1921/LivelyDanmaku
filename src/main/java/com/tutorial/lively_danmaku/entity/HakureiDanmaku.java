package com.tutorial.lively_danmaku.entity;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;
import com.tutorial.lively_danmaku.init.ItemRegistry;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.commands.arguments.ParticleArgument;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.Comparator;
import java.util.List;

import static com.tutorial.lively_danmaku.entity.ai.FlyAndShoot.ShootFromEntityToEntity;

public class HakureiDanmaku extends AbstractDanmaku {
    private int time;
    private boolean isPlayerShoot = false;
    private LivingEntity target;
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final EntityDataAccessor<Integer> BULLET_STAGE = SynchedEntityData.defineId(HakureiDanmaku.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<ParticleOptions> DATA_PARTICLE = SynchedEntityData.defineId(HakureiDanmaku.class, EntityDataSerializers.PARTICLE);
    // 初始速度
    double initialVelocity = 1;
    // 初始位置
    double initialPosition = 1;
    // 阻尼系数
    double dampingFactor = 10;
    public HakureiDanmaku(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }
    public HakureiDanmaku(EntityType<? extends ThrowableItemProjectile> entityType, Level level, String particleType, boolean isPlayerShoot) {
        super(entityType, level);
        this.isPlayerShoot = isPlayerShoot;
        try{
            setParticle(ParticleArgument.readParticle(new StringReader(particleType), BuiltInRegistries.PARTICLE_TYPE.asLookup()));
        }catch (CommandSyntaxException commandsyntaxexception) {
            LOGGER.warn("Couldn't load custom particle {}", particleType, commandsyntaxexception);
        }
    }

    public HakureiDanmaku(EntityType<? extends ThrowableItemProjectile> entityType, Level level, int xRot, int yRot, int lifetime) {
        super(entityType, level);
        this.setXRot(xRot);
        this.setYRot(yRot);
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
        this.getEntityData().define(DATA_PARTICLE, ParticleTypes.END_ROD);
    }

    public ParticleOptions getParticle() {
        return this.getEntityData().get(DATA_PARTICLE);
    }

    public void setParticle(ParticleOptions pParticleOption) {
        this.getEntityData().set(DATA_PARTICLE, pParticleOption);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level() instanceof ClientLevel clientLevel) {
            clientLevel.addParticle(getParticle(),this.getX(),this.getY(),this.getZ(),0,0,0);
        }
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
                    ShootFromEntityToEntity(this, target, this, 0.6F);
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
