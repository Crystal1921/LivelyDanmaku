package com.tutorial.lively_danmaku.blockEntity;

import com.tutorial.lively_danmaku.init.BlockEntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Nameable;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class AdvancedDanmakuTableTE extends BlockEntity implements Nameable {
    public AdvancedDanmakuTableTE(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityTypeRegistry.ADVANCED_DANMAKU_TABLE_ENTITY.get(), blockPos, blockState);
    }

    @Override
    public @NotNull Component getName() {
        return Component.translatable("ui.advanced_danmaku_table");
    }
}
