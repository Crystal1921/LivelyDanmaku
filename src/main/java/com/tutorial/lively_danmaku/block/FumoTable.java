package com.tutorial.lively_danmaku.block;

import com.tutorial.lively_danmaku.blockEntity.FumoTableTE;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FumoTable extends BaseEntityBlock {
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);

    public FumoTable(Properties properties) {
        super(properties);
    }

    public @NotNull RenderShape getRenderShape(@NotNull BlockState blockState) {
        return RenderShape.MODEL;
    }

    public @NotNull InteractionResult use(@NotNull BlockState blockState, Level level, @NotNull BlockPos blockPos, @NotNull Player player, @NotNull InteractionHand interactionHand, @NotNull BlockHitResult result) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity instanceof FumoTableTE itemSupplier) {
            if (itemSupplier.theItem.isEmpty() && player.getAbilities().instabuild) {
                itemSupplier.theItem = player.getItemInHand(interactionHand).copy();
                if (!level.isClientSide) {
                    itemSupplier.setChanged();
                    level.sendBlockUpdated(blockPos, blockState, blockState, Block.UPDATE_CLIENTS);
                }
            } else if (!itemSupplier.theItem.isEmpty() && !player.isCrouching() && !level.isClientSide) {
                ServerPlayer serverPlayer = (ServerPlayer) player;
                ItemStack itemstack1 = itemSupplier.theItem;
                boolean flag = serverPlayer.getInventory().add(itemstack1);
                if (flag && itemstack1.isEmpty()) {
                    itemstack1.setCount(1);
                    ItemEntity itemEntity1 = serverPlayer.drop(itemstack1, false);
                    if (itemEntity1 != null) {
                        itemEntity1.makeFakeItem();
                    }

                    serverPlayer.level().playSound(null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, ((serverPlayer.getRandom().nextFloat() - serverPlayer.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F);
                    serverPlayer.containerMenu.broadcastChanges();
                } else {
                    ItemEntity itementity = serverPlayer.drop(itemstack1, false);
                    if (itementity != null) {
                        itementity.setNoPickUpDelay();
                        itementity.setTarget(serverPlayer.getUUID());
                    }
                }
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new FumoTableTE(blockPos, blockState);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }
}
