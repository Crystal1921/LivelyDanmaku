package com.tutorial.lively_danmaku.entity;

import com.tutorial.lively_danmaku.init.ItemRegistry;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class Broomstick extends PathfinderMob {
    public Broomstick(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.setNoGravity(true);
    }
    public static AttributeSupplier.Builder registerAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.6F)
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        if (!this.level().isClientSide) {
            if (player.isShiftKeyDown()){
                ItemEntity item = new ItemEntity(this.level(), this.getX(), this.getY(1.0D), this.getZ(), ItemRegistry.Broomstick.get().getDefaultInstance());
                this.level().addFreshEntity(item);
                this.discard();
            }
            player.startRiding(this);
        }
        return InteractionResult.sidedSuccess(this.level().isClientSide);
    }

    @Nullable
    public LivingEntity getControllingPassenger() {
        Entity entity = this.getFirstPassenger();
        if (entity instanceof Player player) {
            return player;
        }
        return null;
    }

    protected @NotNull Vec3 getRiddenInput(Player player, @NotNull Vec3 vec3) {
        float f = player.xxa * 0.5F;
        float f1 = player.zza;
        if (f1 <= 0.0F) {
            f1 *= 0.25F;
        }
        return new Vec3(f, f1 * Math.sin(Math.toRadians( - player.getXRot())), f1);
    }

    protected void tickRidden(@NotNull Player player, @NotNull Vec3 vec3) {
        super.tickRidden(player, vec3);
        this.setRot(player.getYRot(), player.getXRot() * 0.5F);
        this.yRotO = this.yBodyRot = this.yHeadRot = this.getYRot();
    }

    protected float getRiddenSpeed(@NotNull Player player) {
        return (float)(this.getAttributeValue(Attributes.MOVEMENT_SPEED));
    }

    public void tick() {
        super.tick();
        this.setNoGravity(true);
        this.resetFallDistance();
    }
}
