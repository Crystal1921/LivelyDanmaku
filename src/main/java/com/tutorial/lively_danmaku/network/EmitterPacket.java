package com.tutorial.lively_danmaku.network;

import com.tutorial.lively_danmaku.blockEntity.DanmakuEmitterTE;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class EmitterPacket{
    float XRot;
    float YRot;
    int freq;
    BlockPos blockPos;
    public EmitterPacket(float XRot, float YRot, int freq, BlockPos blockPos) {
        this.XRot = XRot;
        this.YRot = YRot;
        this.freq = freq;
        this.blockPos = blockPos;
    }

    public static EmitterPacket decode(FriendlyByteBuf friendlyByteBuf) {
        return new EmitterPacket(friendlyByteBuf.readFloat(),friendlyByteBuf.readFloat(),friendlyByteBuf.readInt(),friendlyByteBuf.readBlockPos());
    }

    public void encode(FriendlyByteBuf friendlyByteBuf) {
    friendlyByteBuf.writeFloat(XRot);
    friendlyByteBuf.writeFloat(YRot);
    friendlyByteBuf.writeInt(freq);
    friendlyByteBuf.writeBlockPos(blockPos);
    }

    public void handleOnServer(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            var level = Objects.requireNonNull(context.getSender()).level();
            if (level.getExistingBlockEntity(blockPos) instanceof DanmakuEmitterTE blockEntity) {
                blockEntity.setEmitter(XRot,YRot,freq);
            }
        });
    }
}
