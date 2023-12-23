package com.tutorial.lively_danmaku.block;

import com.tutorial.lively_danmaku.blockEntity.DanmakuEmitterTE;
import com.tutorial.lively_danmaku.init.BlockEntityTypeRegistry;
import com.tutorial.lively_danmaku.init.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DanmakuEmitter extends BaseEntityBlock {
    public static final BooleanProperty RENDER = BlockStateProperties.LIT;
    public DanmakuEmitter(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(RENDER, Boolean.FALSE));
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

    public void onRemove(BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock())) {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof DanmakuEmitterTE) {
                Containers.dropContents(pLevel, pPos, (DanmakuEmitterTE)blockentity);
                pLevel.updateNeighbourForOutputSignal(pPos, this);
            }

            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }
    }

    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, CollisionContext pContext) {
        boolean isRender = pState.getValue(RENDER);
        return (pContext.isHoldingItem(ItemRegistry.DanmakuEmitter.get()) || isRender) ? Shapes.block() : Shapes.empty();
    }

    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        boolean isRender = pState.getValue(RENDER);
        return (isRender)? RenderShape.MODEL : RenderShape.INVISIBLE;
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

    public BlockState getStateForPlacement(@NotNull BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(RENDER, false);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(RENDER);
    }
}
