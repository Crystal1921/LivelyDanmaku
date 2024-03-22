package com.tutorial.lively_danmaku.network;

import com.tutorial.lively_danmaku.capability.CapabilityProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CapabilityS2CPacket {
    float power;
    public CapabilityS2CPacket(float power) {
        this.power = power;
    }

    public static CapabilityS2CPacket decode(FriendlyByteBuf friendlyByteBuf) {
        return new CapabilityS2CPacket(friendlyByteBuf.readFloat());
    }

    public void encode(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeFloat(power);
    }

    public static void handleOnClient(CapabilityS2CPacket message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        if (context.getDirection().getReceptionSide().isClient()) {
            context.enqueueWork(() -> handleCapability(message));
        }
        context.setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private static void handleCapability(CapabilityS2CPacket message) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) {
            return;
        }
        mc.player.getCapability(CapabilityProvider.POWER).ifPresent(cap -> cap.setPower(message.power));
    }
}
