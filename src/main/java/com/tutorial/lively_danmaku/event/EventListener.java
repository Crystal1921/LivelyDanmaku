package com.tutorial.lively_danmaku.event;

import com.tutorial.lively_danmaku.init.EnchantmentRegistry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EventListener {
    @SubscribeEvent
    public static void LivingHurt(LivingHurtEvent event) {
        DamageSource damageSource = event.getSource();
        LivingEntity entity = event.getEntity();
        if (damageSource.is(DamageTypes.THROWN)) {
            int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.danmaku_resistance.get(), entity);
            if (level >= 0) {
                event.setAmount(event.getAmount() * Math.max((1 - level * 0.25F),0));
            }
        }
    }

    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        Player eventPlayer = event.player;
        ItemStack itemStack = eventPlayer.getItemBySlot(EquipmentSlot.CHEST);
    }
}