package com.tutorial.crystal.event;

import com.tutorial.crystal.Enchantment.EnchantmentRegistry;
import com.tutorial.crystal.init.DamageTypeRegistry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EventListener {

    @SubscribeEvent
    public static void LivingHurt(LivingHurtEvent event) {
        DamageSource damageSource = event.getSource();
        LivingEntity player = event.getEntity();
        if (damageSource.is(DamageTypeRegistry.DANMAKU_SHOOT)) {
            int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.danmaku_resistance.get(), player);
            if (level >= 0) {
                event.setAmount(event.getAmount() * (1 - level * 0.25F));
                System.out.println(event.getAmount());
            }
        }
    }
}