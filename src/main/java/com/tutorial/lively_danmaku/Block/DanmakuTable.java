package com.tutorial.lively_danmaku.Block;

import com.tutorial.lively_danmaku.BlockEntity.DanmakuTableEntityBlock;
import com.tutorial.lively_danmaku.GUI.DanmakuMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class DanmakuTable extends BaseEntityBlock {
    public DanmakuTable(Properties properties) {
        super(properties);
    }

    public @NotNull InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos blockPos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            player.openMenu(state.getMenuProvider(level, blockPos));
            return InteractionResult.CONSUME;
        }
    }
    @Nullable
    @Override
    public MenuProvider getMenuProvider(@NotNull BlockState blockState, Level level, @NotNull BlockPos blockPos) {
        BlockEntity blockentity = level.getBlockEntity(blockPos);
        if (blockentity instanceof DanmakuTableEntityBlock) {
            Component component = ((Nameable)blockentity).getDisplayName();
            return new SimpleMenuProvider((id, inventory, player) -> new DanmakuMenu(id, inventory, ContainerLevelAccess.create(level, blockPos)), component);
        } else {
            return null;
        }
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState state) {
        return new DanmakuTableEntityBlock(blockPos,state);
    }
    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }
}
