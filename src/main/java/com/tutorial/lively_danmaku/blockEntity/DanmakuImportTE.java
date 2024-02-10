package com.tutorial.lively_danmaku.blockEntity;

import com.tutorial.lively_danmaku.init.BlockEntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Nameable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DanmakuImportTE extends BlockEntity implements Nameable {
    private NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);
    public DanmakuImportTE(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityTypeRegistry.DANMAKU_IMPORT.get(), pPos, pBlockState);
    }

    @Override
    public Component getName() {
        return Component.translatable("block.lively_danmaku.danmaku_import");
    }
}
