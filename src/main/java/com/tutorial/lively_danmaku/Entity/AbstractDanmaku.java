package com.tutorial.lively_danmaku.Entity;

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
import static java.lang.Math.pow;

public abstract class AbstractDanmaku extends ThrowableItemProjectile {
    public AbstractDanmaku(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }
    @Override
    public void shootFromRotation(@NotNull Entity entity, float pitch, float yaw, float offset, float velocity, float inaccuracy) {
        float f = -Mth.sin(yaw * ((float)Math.PI / 180F)) * Mth.cos(pitch * ((float)Math.PI / 180F));
        float f1 = -Mth.sin((pitch + offset) * ((float)Math.PI / 180F));
        float f2 = Mth.cos(yaw * ((float)Math.PI / 180F)) * Mth.cos(pitch * ((float)Math.PI / 180F));
        this.shoot(f, f1, f2, velocity, inaccuracy);
    }
    @Override
    protected float getGravity() {
        return 0.00F;
    }
    @Override
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        Vec3 vec3 = this.getDeltaMovement();
        double i = Math.sqrt(pow(vec3.x,2) + pow(vec3.y,2) + pow(vec3.z,2)) * 2;
        if (this.getOwner() != null && entity.is(this.getOwner())) {
            entity.hurt(getIndirectEntityDamageSource(this.level(), DamageTypeRegistry.DANMAKU_SHOOT, this.getOwner(), this), (float) i);
        }}
}
