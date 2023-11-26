package com.tutorial.lively_danmaku.network;

import com.tutorial.lively_danmaku.gui.AdvancedDanmakuMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DanmakuNetwork {
    private static final String NETWORK_VERSION = "1";
    private static final ResourceLocation CHANNEL_POSITION = new ResourceLocation("lively_danmaku", "network_point");
    public static final SimpleChannel CHANNEL_POINT = NetworkRegistry.newSimpleChannel(CHANNEL_POSITION, () -> NETWORK_VERSION, NETWORK_VERSION::equals, NETWORK_VERSION::equals);
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        CHANNEL_POINT.registerMessage(0, PointPacket.class, PointPacket::encode, PointPacket::decode, PointPacket::handleOnServer, Optional.of(NetworkDirection.PLAY_TO_SERVER));
    }

    public record PointPacket(int containerId, short x, short y, short number) {
        public static PointPacket decode(FriendlyByteBuf friendlyByteBuf) {
            return new PointPacket(friendlyByteBuf.readVarInt(), friendlyByteBuf.readShort(), friendlyByteBuf.readShort(), friendlyByteBuf.readShort());
        }

        public void encode(FriendlyByteBuf friendlyByteBuf) {
            friendlyByteBuf.writeVarInt(containerId);
            friendlyByteBuf.writeShort(x);
            friendlyByteBuf.writeShort(y);
            friendlyByteBuf.writeShort(number);
        }

        public void handleOnServer(Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            ServerPlayer player = context.getSender();
            if (player == null) {
                context.setPacketHandled(true);
                return;
            }

            AbstractContainerMenu containerMenu = player.containerMenu;
            if (containerMenu.containerId == containerId && containerMenu instanceof AdvancedDanmakuMenu menu) {
                if (menu.stillValid(player) && !player.isSpectator()) {
                    context.enqueueWork(() -> menu.addPointOnServer(player, x, y, number));
                }
            }

            context.setPacketHandled(true);
        }
    }

}
