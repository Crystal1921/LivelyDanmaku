package com.tutorial.crystal.Block;

import com.tutorial.crystal.BlockEntity.ReimuEntityBlock;
import com.tutorial.crystal.init.BlockEntityTypeRegistry;
import com.tutorial.crystal.init.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ReimuBlock extends BaseEntityBlock {
    public ReimuBlock(Properties props) {
        super(props);
    }
    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return new ReimuEntityBlock(pos, state);
    }
    @Override
    public void playerWillDestroy(@NotNull Level level, @NotNull BlockPos blockPos, @NotNull BlockState state, @NotNull Player player) {
        if (!player.isCreative()) {
            ItemEntity item = new ItemEntity(level, blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, new ItemStack(ItemRegistry.ReimuItem.get()));
            level.addFreshEntity(item);
        }
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
        return level.isClientSide() ? null : createTickerHelper(blockEntityType, BlockEntityTypeRegistry.REIMU_ENTITY_BLOCK.get(), ReimuEntityBlock::tick);
    }

    @Deprecated
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult result) {
        player.sendSystemMessage(Component.translatable("chat.crystal.reimu"));
        return InteractionResult.SUCCESS;
    }
}