package com.tutorial.lively_danmaku.blockEntity;

import com.tutorial.lively_danmaku.init.BlockEntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Nameable;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class DanmakuTraceTE extends BlockEntity implements Nameable {
    public DanmakuTraceTE(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityTypeRegistry.DANMAKU_TRACE.get(), pPos, pBlockState);
    }

    @Override
    public @NotNull Component getName() {
        return Component.translatable("block.lively_danmaku.danmaku_trace");
    }
}
