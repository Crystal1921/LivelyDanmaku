package com.tutorial.lively_danmaku.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class YinYangOrb extends ThrowableItemProjectile {
    public YinYangOrb(EntityType<? extends ThrowableItemProjectile> p_37442_, Level p_37443_) {
        super(p_37442_, p_37443_);
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }
    @Override
    protected float getGravity() {
        return 0.00F;
    }
}
