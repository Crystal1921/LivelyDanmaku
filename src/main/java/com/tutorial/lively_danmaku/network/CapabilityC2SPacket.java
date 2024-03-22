package com.tutorial.lively_danmaku.network;

import com.tutorial.lively_danmaku.capability.CapabilityProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CapabilityC2SPacket {
    float power;
    public CapabilityC2SPacket(float power) {
        this.power = power;
    }

    public static CapabilityC2SPacket decode(FriendlyByteBuf friendlyByteBuf) {
        return new CapabilityC2SPacket(friendlyByteBuf.readFloat());
    }

    public void encode(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeFloat(power);
    }

    public static void handleOnServer(CapabilityC2SPacket message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        ServerPlayer player = context.getSender();
        context.enqueueWork(() -> {
            player.getCapability(CapabilityProvider.POWER).ifPresent(cap -> cap.setPower(message.power));
        });
        context.setPacketHandled(true);
    }
}
