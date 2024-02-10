package com.tutorial.lively_danmaku.network;

import com.tutorial.lively_danmaku.gui.menu.AdvancedDanmakuMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PointPacket {
    int containerId;
    short x;
    short y;
    short number;

    public PointPacket(int containerId, short x, short y, short number) {
        this.containerId = containerId;
        this.x = x;
        this.y = y;
        this.number = number;
    }

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
