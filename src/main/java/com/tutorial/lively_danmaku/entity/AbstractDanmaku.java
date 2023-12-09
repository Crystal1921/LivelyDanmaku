package com.tutorial.lively_danmaku.entity;

import com.tutorial.lively_danmaku.init.DamageTypeRegistry;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import static com.tutorial.lively_danmaku.data.RegistryDataGenerator.getIndirectEntityDamageSource;

public abstract class AbstractDanmaku extends ThrowableItemProjectile {
    public AbstractDanmaku(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public void shootFromRotation(float pitch, float yaw, float offset, float velocity, float inaccuracy, boolean isWiggle) {
        float f = -Mth.sin(yaw * ((float)Math.PI / 180F)) * Mth.cos(pitch * ((float)Math.PI / 180F));
        float f1 = -Mth.sin((pitch + offset) * ((float)Math.PI / 180F));
        float f2 = Mth.cos(yaw * ((float)Math.PI / 180F)) * Mth.cos(pitch * ((float)Math.PI / 180F));
        this.shoot(f, f1, f2, velocity, inaccuracy,isWiggle);
    }

    public void shoot(double pX, double pY, double pZ, float pVelocity, float pInaccuracy,boolean isWiggle) {
        Vec3 vec3 = (new Vec3(pX, pY, pZ)).normalize();
        if (isWiggle) {
            vec3.add(this.random.triangle(0.0D, 0.0172275D * (double) pInaccuracy), this.random.triangle(0.0D, 0.0172275D * (double) pInaccuracy), this.random.triangle(0.0D, 0.0172275D * (double) pInaccuracy));
        }
        vec3.scale(pVelocity);
        this.setDeltaMovement(vec3);
        double d0 = vec3.horizontalDistance();
        this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * (double)(180F / (float)Math.PI)));
        this.setXRot((float)(Mth.atan2(vec3.y, d0) * (double)(180F / (float)Math.PI)));
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
    }
    @Override
    protected float getGravity() {
        return 0.00F;
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        double i = Math.sqrt(this.distanceToSqr(this.getDeltaMovement())) / 20;
        if (!(entity instanceof Reimu)) {
            entity.hurt(getIndirectEntityDamageSource(this.level(), DamageTypeRegistry.DANMAKU_SHOOT, this.getOwner(), this), (float)i);
        }
    }
}
