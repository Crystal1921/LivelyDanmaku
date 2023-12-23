package com.tutorial.lively_danmaku.network;

import com.tutorial.lively_danmaku.blockEntity.DanmakuEmitterTE;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

import static com.tutorial.lively_danmaku.block.DanmakuEmitter.RENDER;

public class EmitterPacket{
    float XRot;
    float YRot;
    int freq;
    float deltaX;
    float deltaY;
    float deltaZ;
    boolean isRender;
    BlockPos blockPos;
    public EmitterPacket(float XRot, float YRot, int freq, BlockPos blockPos, float deltaX, float deltaY, float deltaZ, boolean isRender) {
        this.XRot = XRot;
        this.YRot = YRot;
        this.freq = freq;
        this.blockPos = blockPos;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.deltaZ = deltaZ;
        this.isRender = isRender;
    }

    public static EmitterPacket decode(FriendlyByteBuf friendlyByteBuf) {
        return new EmitterPacket(friendlyByteBuf.readFloat(),friendlyByteBuf.readFloat(),friendlyByteBuf.readInt(),friendlyByteBuf.readBlockPos(),friendlyByteBuf.readFloat(),friendlyByteBuf.readFloat(),friendlyByteBuf.readFloat(),friendlyByteBuf.readBoolean());
    }

    public void encode(FriendlyByteBuf friendlyByteBuf) {
    friendlyByteBuf.writeFloat(XRot);
    friendlyByteBuf.writeFloat(YRot);
    friendlyByteBuf.writeInt(freq);
    friendlyByteBuf.writeBlockPos(blockPos);
    friendlyByteBuf.writeFloat(deltaX);
    friendlyByteBuf.writeFloat(deltaY);
    friendlyByteBuf.writeFloat(deltaZ);
    friendlyByteBuf.writeBoolean(isRender);
    }

    public void handleOnServer(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            var level = Objects.requireNonNull(context.getSender()).level();
            if (level.getExistingBlockEntity(blockPos) instanceof DanmakuEmitterTE blockEntity) {
                blockEntity.setEmitter(XRot,YRot,freq,deltaX,deltaY,deltaZ);
                BlockState blockState = level.getBlockState(blockPos);
                BlockState blockState1 = blockState.setValue(RENDER, isRender);
                level.setBlock(blockPos,blockState1,2);
                blockEntity.setBlockState(blockState1);
                level.getChunkAt(blockPos).setBlockEntity(blockEntity);
            }
        });
    }
}
