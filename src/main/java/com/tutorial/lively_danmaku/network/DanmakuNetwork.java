package com.tutorial.lively_danmaku.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DanmakuNetwork {
    private static final String NETWORK_VERSION = "1";
    private static final ResourceLocation CHANNEL_POSITION = new ResourceLocation("lively_danmaku", "network_point");
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(CHANNEL_POSITION, () -> NETWORK_VERSION, NETWORK_VERSION::equals, NETWORK_VERSION::equals);
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        CHANNEL.registerMessage(0, PointPacket.class, PointPacket::encode, PointPacket::decode, PointPacket::handleOnServer,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));
        CHANNEL.registerMessage(1, EmitterPacket.class, EmitterPacket::encode, EmitterPacket::decode, EmitterPacket::handleOnServer,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));
        CHANNEL.registerMessage(2, PointListPacket.class, PointListPacket::encode, PointListPacket::decode, PointListPacket::handleOnServer,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));
        CHANNEL.registerMessage(3, ColorPacket.class, ColorPacket::encode, ColorPacket::decode, ColorPacket::handleOnServer,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));
        CHANNEL.registerMessage(4, CapabilityC2SPacket.class, CapabilityC2SPacket::encode, CapabilityC2SPacket::decode, CapabilityC2SPacket::handleOnServer,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));
        CHANNEL.registerMessage(5, CapabilityS2CPacket.class, CapabilityS2CPacket::encode, CapabilityS2CPacket::decode, CapabilityS2CPacket::handleOnClient,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    }

    public static void sendToClient(Object message, Player player) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), message);
    }
}
