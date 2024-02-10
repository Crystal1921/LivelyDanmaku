package com.tutorial.lively_danmaku.network;

import com.google.common.collect.Lists;
import com.tutorial.lively_danmaku.gui.menu.DanmakuImportMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.function.Supplier;

public class PointListPacket {
    ArrayList<Long> list;
    public PointListPacket(ArrayList<Long> list) {
        this.list = list;
    }

    public static PointListPacket decode (FriendlyByteBuf buf) {
        return new PointListPacket(buf.readCollection(Lists::newArrayListWithCapacity, FriendlyByteBuf::readLong));
    }

    public void encode (FriendlyByteBuf buf) {
        buf.writeCollection(list,FriendlyByteBuf::writeLong);
    }

    public void handleOnServer(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        ServerPlayer player = context.getSender();
        if (player == null) {
            context.setPacketHandled(true);
            return;
        }
        AbstractContainerMenu containerMenu = player.containerMenu;
        if (containerMenu instanceof DanmakuImportMenu menu) {
            if (menu.stillValid(player) && !player.isSpectator()) {
                context.enqueueWork(() -> menu.setImageString(list));
            }
        }
    }
}
