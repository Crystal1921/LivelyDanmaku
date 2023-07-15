package com.tutorial.lively_danmaku.event;

import com.tutorial.lively_danmaku.init.EnchantmentRegistry;
import com.tutorial.lively_danmaku.init.DamageTypeRegistry;
import com.tutorial.lively_danmaku.init.ItemRegistry;
import net.minecraft.world.damagesource.DamageSource;
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
        LivingEntity player = event.getEntity();
        if (damageSource.is(DamageTypeRegistry.DANMAKU_SHOOT)) {
            int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.danmaku_resistance.get(), player);
            if (level >= 0) {
                event.setAmount(event.getAmount() * (1 - level * 0.25F));
                System.out.println(event.getAmount());
            }
        }
    }

    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        Player eventPlayer = event.player;
        ItemStack itemStack = eventPlayer.getItemBySlot(EquipmentSlot.HEAD);
        if (!eventPlayer.isCreative()) {
            if (itemStack.is(ItemRegistry.ReimuHeaddress.get())) {
                eventPlayer.getAbilities().mayfly = true;
            }   else {
                eventPlayer.getAbilities().mayfly = false;
                eventPlayer.getAbilities().flying = false;
            }
        }
    }
}