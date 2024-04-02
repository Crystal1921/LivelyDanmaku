package com.tutorial.lively_danmaku.network;

import com.tutorial.lively_danmaku.gui.menu.DanmakuTraceMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ParticleTypePacket {
    String particleType;
    public ParticleTypePacket(String particleType){
        this.particleType = particleType;
    }
    public static ParticleTypePacket decode (FriendlyByteBuf buf) {
        return new ParticleTypePacket(buf.readUtf());
    }

    public void encode (FriendlyByteBuf buf) {
        buf.writeUtf(particleType);
    }

    public void handleOnServer(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        ServerPlayer player = context.getSender();
        if (player == null) {
            context.setPacketHandled(true);
            return;
        }
        AbstractContainerMenu containerMenu = player.containerMenu;
        if (containerMenu instanceof DanmakuTraceMenu menu) {
            if (menu.stillValid(player) && !player.isSpectator()) {
                context.enqueueWork(() -> menu.setItem(particleType));
            }
        }
        context.setPacketHandled(true);
    }
}
