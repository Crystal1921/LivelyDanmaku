package com.tutorial.lively_danmaku.init;

import com.tutorial.lively_danmaku.blockEntity.*;
import com.tutorial.lively_danmaku.blockEntity.render.FumoTableRender;
import com.tutorial.lively_danmaku.blockEntity.render.ReimuTileEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityTypeRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, "lively_danmaku");
    public static final RegistryObject<BlockEntityType<ReimuEntityTE>> REIMU_ENTITY_BLOCK = BLOCK_ENTITY_TYPES.register("reimu_block.json",
            () -> BlockEntityType.Builder.of(ReimuEntityTE::new, BlockRegistry.REIMU_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<DanmakuTableTE>> DANMAKU_TABLE_ENTITY = BLOCK_ENTITY_TYPES.register("danmaku_table",
            () -> BlockEntityType.Builder.of(DanmakuTableTE::new, BlockRegistry.DANMAKU_TABLE.get()).build(null));
    public static final RegistryObject<BlockEntityType<FumoTableTE>> FUMO_TABLE = BLOCK_ENTITY_TYPES.register("fumo_table",
            () -> BlockEntityType.Builder.of(FumoTableTE::new, BlockRegistry.FUMO_TABLE.get()).build(null));
    public static final RegistryObject<BlockEntityType<AdvancedDanmakuTableTE>> ADVANCED_DANMAKU_TABLE_ENTITY = BLOCK_ENTITY_TYPES.register("advanced_danmaku_table",
            () -> BlockEntityType.Builder.of(AdvancedDanmakuTableTE::new, BlockRegistry.ADVANCED_DANMAKU_TABLE.get()).build(null));
    public static final RegistryObject<BlockEntityType<DanmakuEmitterTE>> DANMAKU_EMITTER = BLOCK_ENTITY_TYPES.register("danmaku_emitter",
            () -> BlockEntityType.Builder.of(DanmakuEmitterTE::new, BlockRegistry.DANMAKU_EMITTER.get()).build(null));
    public static final RegistryObject<BlockEntityType<DanmakuImportTE>> DANMAKU_IMPORT = BLOCK_ENTITY_TYPES.register("danmaku_import",
            () -> BlockEntityType.Builder.of(DanmakuImportTE::new, BlockRegistry.DANMAKU_IMPORT.get()).build(null));
    @OnlyIn(Dist.CLIENT)
    public static void registerTileEntityRenders() {
        BlockEntityRenderers.register(REIMU_ENTITY_BLOCK.get(), ReimuTileEntityRenderer::new);
        BlockEntityRenderers.register(FUMO_TABLE.get(), FumoTableRender::new);
    }
}
