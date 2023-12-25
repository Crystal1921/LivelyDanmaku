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

import java.util.Random;

public class StarDanmaku extends AbstractDanmaku {
    private static final EntityDataAccessor<Boolean> IS_TICK = SynchedEntityData.defineId(StarDanmaku.class, EntityDataSerializers.BOOLEAN);
    private final int color;
    private int tickTime;
    Random random = new Random();

    public StarDanmaku(EntityType<? extends ThrowableItemProjectile> p_37442_, Level p_37443_) {
        super(p_37442_, p_37443_);
        this.getEntityData().set(IS_TICK,true);
        this.color = random.nextInt(3);
    }

    protected void onHit(@NotNull HitResult hitResult) {
        super.onHit(hitResult);
        if (!level().isClientSide) {
            level().broadcastEntityEvent(this, (byte)3);
            boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(level(), this.getOwner());
            level().explode(this, this.getX(), this.getY(), this.getZ(), 1, flag, Level.ExplosionInteraction.BLOCK);
            this.discard();
        }
    }
    @Override
    public void tick() {
        super.tick();
        if (this.getEntityData().get(IS_TICK)) this.tickTime++;
        if (tickTime >= 100 + color * 5) {
            this.discard();
        }
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegistry.ItemStarDanmaku.get();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(IS_TICK,true);
    }

    public int getColor() {
        return color;
    }

    @Override
    public void setIsTick(boolean isTick) {
        this.getEntityData().set(IS_TICK,isTick);
    }
}
