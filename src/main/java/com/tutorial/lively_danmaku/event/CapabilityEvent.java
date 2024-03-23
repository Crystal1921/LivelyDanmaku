package com.tutorial.lively_danmaku.event;

import com.tutorial.lively_danmaku.Utils;
import com.tutorial.lively_danmaku.capability.CapabilityProvider;
import com.tutorial.lively_danmaku.capability.PowerCapability;
import com.tutorial.lively_danmaku.network.CapabilityS2CPacket;
import com.tutorial.lively_danmaku.network.DanmakuNetwork;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CapabilityEvent {
    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        Player original = event.getOriginal();
        Player newPlayer = event.getEntity();
        original.reviveCaps();
        LazyOptional<PowerCapability> oldCap = original.getCapability(CapabilityProvider.POWER);
        LazyOptional<PowerCapability> newCap = newPlayer.getCapability(CapabilityProvider.POWER);
        newCap.ifPresent((newPower) -> oldCap.ifPresent((oldPower) -> {
            if (event.isWasDeath()) {
                newPower.setPower(oldPower.getPower() - 1);
            } else {
                newPower.copyFrom(oldPower);
            }
        }));
        original.invalidateCaps();
    }

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Player player) {
            player.getCapability(CapabilityProvider.POWER).ifPresent(PowerCapability::markChanged);
        }
    }

    @SubscribeEvent
    public static void playerTickEvent(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.END) {
            LazyOptional<PowerCapability> Cap = player.getCapability(CapabilityProvider.POWER);
            Cap.ifPresent(powerCapability -> {
                if(powerCapability.isChanged()) {
                    DanmakuNetwork.sendToClient(new CapabilityS2CPacket(powerCapability.getPower()),player);
                    powerCapability.setChanged(false);
                }
            });
        }
    }

    @SubscribeEvent
    public static void onAttachment (AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player player) {
            if(!player.getCapability(CapabilityProvider.POWER).isPresent()) {
                event.addCapability(new ResourceLocation(Utils.MOD_ID,"power"),new CapabilityProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onRegistryCap (RegisterCapabilitiesEvent event) {
        event.register(PowerCapability.class);
    }
}