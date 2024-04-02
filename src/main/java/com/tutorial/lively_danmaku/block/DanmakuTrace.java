package com.tutorial.lively_danmaku.block;

import com.tutorial.lively_danmaku.blockEntity.DanmakuImportTE;
import com.tutorial.lively_danmaku.blockEntity.DanmakuTraceTE;
import com.tutorial.lively_danmaku.gui.menu.DanmakuImportMenu;
import com.tutorial.lively_danmaku.gui.menu.DanmakuTraceMenu;
import com.tutorial.lively_danmaku.init.BlockEntityTypeRegistry;
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
import org.jetbrains.annotations.Nullable;

public class DanmakuTrace extends BaseEntityBlock {
    public DanmakuTrace(Properties pProperties) {
        super(pProperties);
    }

    public @NotNull InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos blockPos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            player.openMenu(state.getMenuProvider(level, blockPos));
            return InteractionResult.CONSUME;
        }
    }

    @javax.annotation.Nullable
    @Override
    public MenuProvider getMenuProvider(@NotNull BlockState blockState, Level level, @NotNull BlockPos blockPos) {
        BlockEntity blockentity = level.getBlockEntity(blockPos);
        if (blockentity instanceof DanmakuTraceTE) {
            Component component = ((Nameable)blockentity).getDisplayName();
            return new SimpleMenuProvider((id, inventory, player) -> new DanmakuTraceMenu(id,inventory, ContainerLevelAccess.create(level,blockPos)), component);
        } else {
            return null;
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new DanmakuTraceTE(pPos,pState);
    }
    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.MODEL;
    }
}
