package com.tutorial.lively_danmaku.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class FakePlayer extends PathfinderMob {
    public static final EntityDataAccessor<Boolean> IS_DISCOVERED = SynchedEntityData.defineId(FakePlayer.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> IS_SMALL = SynchedEntityData.defineId(FakePlayer.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> EASTER_EGG = SynchedEntityData.defineId(FakePlayer.class, EntityDataSerializers.BOOLEAN);
    public FakePlayer(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }
    public static AttributeSupplier.Builder registerAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(IS_DISCOVERED, false);
        this.getEntityData().define(IS_SMALL, false);
        this.getEntityData().define(EASTER_EGG, false);
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        this.refreshDimensions();
        super.onSyncedDataUpdated(pKey);
    }

    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        if (!this.level().isClientSide && player.isShiftKeyDown()) {
            this.getEntityData().set(IS_SMALL, !this.getEntityData().get(IS_SMALL));
        }
        return InteractionResult.sidedSuccess(this.level().isClientSide);
    }

    public @NotNull EntityDimensions getDimensions(@NotNull Pose pPose) {
        if (this.getEntityData().get(IS_SMALL)) {
            return super.getDimensions(pPose).scale(0.5F);
        }   else return super.getDimensions(pPose);
    }

    public void tick() {
        super.tick();
        if ((this.getCustomName() != null) && (this.getCustomName().getString().equals("Crystal1921"))) {
            this.getEntityData().set(EASTER_EGG, true);
            if (!this.getEntityData().get(IS_DISCOVERED)) {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player != null && this.level() instanceof ClientLevel) {
                    mc.player.sendSystemMessage(Component.translatable("chat.lively_danmaku.easter_egg"));
                }
                this.getEntityData().set(IS_DISCOVERED,true);
            }
        }   else this.getEntityData().set(EASTER_EGG, false);
    }
}
