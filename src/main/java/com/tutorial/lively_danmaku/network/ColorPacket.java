package com.tutorial.lively_danmaku.network;

import com.tutorial.lively_danmaku.gui.menu.DanmakuColorMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ColorPacket {
    int color;
    int containerId;
    public ColorPacket (int color,int containerId) {
        this.color = color;
        this.containerId = containerId;
    }

    public static ColorPacket decode(FriendlyByteBuf friendlyByteBuf) {
        return new ColorPacket(friendlyByteBuf.readVarInt(),friendlyByteBuf.readVarInt());
    }

    public void encode(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeVarInt(color);
        friendlyByteBuf.writeVarInt(containerId);
    }

    public void handleOnServer(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        ServerPlayer player = context.getSender();
        if (player == null) {
            context.setPacketHandled(true);
            return;
        }

        AbstractContainerMenu containerMenu = player.containerMenu;
        if (containerMenu.containerId == containerId && containerMenu instanceof DanmakuColorMenu menu) {
            if (menu.stillValid(player) && !player.isSpectator()) {
                context.enqueueWork(() -> menu.setRGB(color));
            }
        }
    }
}
