package com.tutorial.crystal.Block;

import com.tutorial.crystal.BlockEntity.ReimuEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class Crystal1921Block extends BaseEntityBlock {
    public Crystal1921Block(Properties props) {
        super(props);
    }
    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return new ReimuEntityBlock(pos, state);
    }
}
