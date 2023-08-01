package com.tutorial.lively_danmaku.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class P_Point extends Item {
    public P_Point(Properties properties,int nutrition) {
        super(properties.food(new FoodProperties.Builder().nutrition(nutrition).saturationMod(0.3F).fast().alwaysEat().effect(new MobEffectInstance(MobEffects.REGENERATION,nutrition * 20,0),1.0F).build()));
    }
}
