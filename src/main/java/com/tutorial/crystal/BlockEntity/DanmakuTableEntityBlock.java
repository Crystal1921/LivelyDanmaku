package com.tutorial.crystal.BlockEntity;

import com.tutorial.crystal.init.BlockEntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Nameable;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class DanmakuTableEntityBlock extends BlockEntity implements Nameable {
    public DanmakuTableEntityBlock(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityTypeRegistry.DANMAKU_TABLE_ENTITY_BLOCK.get(),blockPos, blockState);
    }

    @Override
    public @NotNull Component getName() {
        return Component.translatable("ui.danmaku_table");
    }
}
