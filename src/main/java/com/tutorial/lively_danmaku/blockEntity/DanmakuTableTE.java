package com.tutorial.lively_danmaku.blockEntity;

import com.tutorial.lively_danmaku.init.BlockEntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Nameable;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class DanmakuTableTE extends BlockEntity implements Nameable {
    public DanmakuTableTE(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityTypeRegistry.DANMAKU_TABLE_ENTITY.get(),blockPos, blockState);
    }

    @Override
    public @NotNull Component getName() {
        return Component.translatable("ui.danmaku_table");
    }
}
