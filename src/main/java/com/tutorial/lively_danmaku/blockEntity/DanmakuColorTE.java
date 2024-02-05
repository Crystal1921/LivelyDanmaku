package com.tutorial.lively_danmaku.blockEntity;

import com.tutorial.lively_danmaku.init.BlockEntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Nameable;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DanmakuColorTE extends BlockEntity implements Nameable {
    public DanmakuColorTE(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityTypeRegistry.DANMAKU_COLOR.get(), pPos, pBlockState);
    }

    @Override
    public Component getName() {
        return Component.translatable("block.lively_danmaku.danmaku_color");
    }
}
