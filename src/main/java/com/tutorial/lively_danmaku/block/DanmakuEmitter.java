package com.tutorial.lively_danmaku.block;

import com.tutorial.lively_danmaku.blockEntity.DanmakuEmitterTE;
import com.tutorial.lively_danmaku.init.BlockEntityTypeRegistry;
import com.tutorial.lively_danmaku.init.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DanmakuEmitter extends BaseEntityBlock {
    public DanmakuEmitter(Properties pProperties) {
        super(pProperties);
    }

    public @NotNull InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos blockPos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if (blockEntity instanceof DanmakuEmitterTE danmakuEmitterTE) {
                NetworkHooks.openScreen(serverPlayer,danmakuEmitterTE,blockPos);
            }
            return InteractionResult.CONSUME;
        }
    }

    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, CollisionContext pContext) {
        return pContext.isHoldingItem(ItemRegistry.DanmakuEmitter.get()) ? Shapes.block() : Shapes.empty();
    }

    public @NotNull RenderShape getRenderShape(BlockState pState) {
        return RenderShape.INVISIBLE;
    }

    @javax.annotation.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
        return level.isClientSide() ? null : createTickerHelper(blockEntityType, BlockEntityTypeRegistry.DANMAKU_EMITTER.get(), DanmakuEmitterTE::tick);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new DanmakuEmitterTE(pPos,pState);
    }
}
