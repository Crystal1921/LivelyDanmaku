package com.tutorial.lively_danmaku.Block;

import com.tutorial.lively_danmaku.BlockEntity.FumoTableEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FumoTable extends BaseEntityBlock {
    public FumoTable(Properties properties) {
        super(properties);
    }

    public @NotNull RenderShape getRenderShape(@NotNull BlockState blockState) {
        return RenderShape.MODEL;
    }

    public @NotNull InteractionResult use(@NotNull BlockState blockState, Level level, @NotNull BlockPos blockPos, @NotNull Player player, @NotNull InteractionHand interactionHand, @NotNull BlockHitResult result) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if (blockEntity instanceof FumoTableEntityBlock fumoTable) {
                if (player.isCreative() && player.isCrouching()) {
                    NetworkHooks.openScreen(serverPlayer, fumoTable, blockPos );
                } else if (fumoTable.getItem() != null) {
                    ItemStack itemstack1 = fumoTable.getItem();
                    boolean flag = serverPlayer.getInventory().add(itemstack1);
                    if (flag && itemstack1.isEmpty()) {
                        itemstack1.setCount(1);
                        ItemEntity itemEntity1 = serverPlayer.drop(itemstack1, false);
                        if (itemEntity1 != null) {
                            itemEntity1.makeFakeItem();
                        }

                        serverPlayer.level().playSound((Player)null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, ((serverPlayer.getRandom().nextFloat() - serverPlayer.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F);
                        serverPlayer.containerMenu.broadcastChanges();
                    } else {
                        ItemEntity itementity = serverPlayer.drop(itemstack1, false);
                        if (itementity != null) {
                            itementity.setNoPickUpDelay();
                            itementity.setTarget(serverPlayer.getUUID());
                        }
                    }
                }
            }   else throw new IllegalStateException("Our Container provider is missing");
            return InteractionResult.CONSUME;
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new FumoTableEntityBlock(blockPos,blockState);
    }
}
