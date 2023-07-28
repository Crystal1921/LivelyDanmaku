package com.tutorial.lively_danmaku.Entity;

import com.tutorial.lively_danmaku.init.ItemRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class StarDanmaku extends AbstractDanmaku {
    public StarDanmaku(EntityType<? extends ThrowableItemProjectile> p_37442_, Level p_37443_) {
        super(p_37442_, p_37443_);
        this.color = random.nextInt(3);
    }

    private final int color;
    Random random = new Random();
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
        if (tickCount >= 100 + color * 5) {
            this.discard();
        }
    }
    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegistry.ItemStarDanmaku.get();
    }
    public int getColor() {return color;}
}
