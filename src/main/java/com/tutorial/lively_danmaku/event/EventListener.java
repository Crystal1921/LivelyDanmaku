package com.tutorial.lively_danmaku.event;

import com.tutorial.lively_danmaku.Utils;
import com.tutorial.lively_danmaku.capability.CapabilityProvider;
import com.tutorial.lively_danmaku.capability.PowerCapability;
import com.tutorial.lively_danmaku.init.DamageTypeRegistry;
import com.tutorial.lively_danmaku.init.EnchantmentRegistry;
import com.tutorial.lively_danmaku.init.ItemRegistry;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EventListener {
    @SubscribeEvent
    public static void LivingHurt(LivingHurtEvent event) {
        DamageSource damageSource = event.getSource();
        LivingEntity entity = event.getEntity();
        if (damageSource.is(DamageTypeRegistry.DANMAKU_SHOOT)) {
            int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.danmaku_resistance.get(), entity);
            if (level >= 0) {
                event.setAmount(event.getAmount() * Math.max((1 - level * 0.25F),0));
            }
        }
    }

    @SubscribeEvent
    public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && player.gameMode.isSurvival()){
            boolean isReimuHeaddress = event.getTo().is(ItemRegistry.ReimuHeaddress.get());
            player.getAbilities().mayfly = isReimuHeaddress;
            player.getAbilities().flying = isReimuHeaddress;
            player.connection.send(new ClientboundPlayerAbilitiesPacket(player.getAbilities()));
            player.refreshDimensions();
        }
    }

    @SubscribeEvent
    public static void onSizeChange(EntityEvent.Size event) {
        if (event.getEntity() instanceof Player player && event.getEntity().isAddedToWorld()){
            if (player.getInventory().getArmor(3).is(ItemRegistry.ReimuHeaddress.get()) && event.getPose() == Pose.CROUCHING) {
                event.setNewSize(new EntityDimensions(0.6F,0.6F,true),true);
            }
        }
    }

    @SubscribeEvent
    public static void onAttachment (AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player player) {
            if(!player.getCapability(CapabilityProvider.POWER).isPresent()) {
                event.addCapability(new ResourceLocation(Utils.MOD_ID,"fly"),new CapabilityProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onRegistryCap (RegisterCapabilitiesEvent event) {
        event.register(PowerCapability.class);
    }
}