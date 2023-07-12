package com.tutorial.crystal.init;

import com.tutorial.crystal.Block.DanmakuCraftingTable;
import com.tutorial.crystal.Block.ReimuBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "crystal");
    public static final RegistryObject<Block> REIMU_BLOCK = BLOCKS.register("reimu_block",
            () -> new ReimuBlock(BlockBehaviour.Properties.of().strength(0.5F, 1200.0F).lightLevel((light) -> 15)));
    public static final RegistryObject<Block> DANMAKU_TABLE = BLOCKS.register("danmaku_table", () -> new DanmakuCraftingTable(BlockBehaviour.Properties.of().ignitedByLava().mapColor(MapColor.FIRE).sound(SoundType.WOOD).strength(2.5F)));
}
