package com.tutorial.lively_danmaku.item;

import com.tutorial.lively_danmaku.capability.CapabilityProvider;
import com.tutorial.lively_danmaku.network.CapabilityC2SPacket;
import com.tutorial.lively_danmaku.network.DanmakuNetwork;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class PowerPoint extends Item {
    public PowerPoint(Properties properties, int nutrition) {
        super(properties.food(new FoodProperties.Builder()
                        .nutrition(nutrition)
                        .saturationMod(0.3F)
                        .fast()
                        .alwaysEat()
                        .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, nutrition * 20, 0), 1.0F)
                        .build()));
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pEntityLiving) {
        if (pEntityLiving instanceof Player player && pLevel.isClientSide) {
            float nutrition;
            FoodProperties foodProperties = this.getFoodProperties();
            if (foodProperties != null) {
                nutrition = foodProperties.getNutrition();
            } else {
                nutrition = 1;
            }

            player.getCapability(CapabilityProvider.POWER)
                    .ifPresent(powerCapability
                            -> DanmakuNetwork.CHANNEL.sendToServer(new CapabilityC2SPacket(powerCapability.getPower() + nutrition * 0.05F)));
        }
        return super.finishUsingItem(pStack,pLevel,pEntityLiving);
    }
}
