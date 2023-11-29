package com.tutorial.lively_danmaku.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DanmakuNetwork {
    private static final String NETWORK_VERSION = "1";
    private static final ResourceLocation CHANNEL_POSITION = new ResourceLocation("lively_danmaku", "network_point");
    public static final SimpleChannel CHANNEL_POINT = NetworkRegistry.newSimpleChannel(CHANNEL_POSITION, () -> NETWORK_VERSION, NETWORK_VERSION::equals, NETWORK_VERSION::equals);
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        CHANNEL_POINT.registerMessage(0, PointPacket.class, PointPacket::encode, PointPacket::decode, PointPacket::handleOnServer,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));
        CHANNEL_POINT.registerMessage(1, EmitterPacket.class, EmitterPacket::encode, EmitterPacket::decode, EmitterPacket::handleOnServer,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));
    }
}
