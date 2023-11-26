package com.tutorial.lively_danmaku.blockEntity;

import com.tutorial.lively_danmaku.init.BlockEntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ReimuEntityTE extends BlockEntity {
    public ReimuEntityTE(BlockPos worldPosition, BlockState blockState) {
        super(BlockEntityTypeRegistry.REIMU_ENTITY_BLOCK.get(), worldPosition, blockState);
    }
    private int count = 0;

    public static void tick(Level level, BlockPos pos, BlockState state, ReimuEntityTE entity) {
        entity.count += 1;
        if (entity.count > 100) {
            entity.count = 0;
            if (level != null && !level.isClientSide()) {
                Player player = level.getNearestPlayer(pos.getX(), pos.getY(), pos.getZ(), 1.0, false);
                if (player != null) {
                    player.sendSystemMessage(Component.translatable("chat.lively_danmaku.welcome"));
                }
            }
        }
    }
}