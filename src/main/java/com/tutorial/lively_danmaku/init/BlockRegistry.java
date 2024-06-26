package com.tutorial.lively_danmaku.init;

import com.tutorial.lively_danmaku.block.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "lively_danmaku");
    public static final RegistryObject<Block> REIMU_BLOCK = BLOCKS.register("reimu_block",
            () -> new ReimuBlock(BlockBehaviour.Properties.of().noCollission().strength(0.5F, 1200.0F).lightLevel((light) -> 15)));
    public static final RegistryObject<Block> DANMAKU_TABLE = BLOCKS.register("danmaku_table",
            () -> new DanmakuTable(BlockBehaviour.Properties.of().ignitedByLava().mapColor(MapColor.FIRE).sound(SoundType.WOOD).strength(2.5F).noOcclusion()));
    public static final RegistryObject<Block> FUMO_TABLE = BLOCKS.register("fumo_table",
            () -> new FumoTable(BlockBehaviour.Properties.of().strength(2.5F).noOcclusion()));
    public static final RegistryObject<Block> ADVANCED_DANMAKU_TABLE = BLOCKS.register("advanced_danmaku_table",
            () -> new AdvancedDanmakuTable(BlockBehaviour.Properties.of().ignitedByLava().mapColor(MapColor.FIRE).sound(SoundType.WOOD).strength(2.5F).noOcclusion()));
    public static final RegistryObject<Block> DANMAKU_EMITTER = BLOCKS.register("danmaku_emitter",
            () -> new DanmakuEmitter(BlockBehaviour.Properties.of().strength(-1.0F, 3600000.8F).noLootTable().noOcclusion().noCollission().noParticlesOnBreak().pushReaction(PushReaction.BLOCK)));
    public static final RegistryObject<Block> DANMAKU_IMPORT = BLOCKS.register("danmaku_import",
            () -> new DanmakuImport(BlockBehaviour.Properties.of().strength(2.5F).noOcclusion()));
    public static final RegistryObject<Block> DANMAKU_COLOR = BLOCKS.register("danmaku_color",
            () -> new DanmakuColor(BlockBehaviour.Properties.of().strength(2.5F).noOcclusion()));
    public static final RegistryObject<Block> DANMAKU_TRACE = BLOCKS.register("danmaku_trace",
            () -> new DanmakuTrace(BlockBehaviour.Properties.of().strength(2.5F).noOcclusion()));
    public static final RegistryObject<Block> DANMAKU_FUNCTION = BLOCKS.register("danmaku_function",
            () -> new DanmakuFunction(BlockBehaviour.Properties.of().strength(2.5F).noOcclusion()));
}
