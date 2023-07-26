package com.tutorial.lively_danmaku.init;

import com.tutorial.lively_danmaku.BlockEntity.DanmakuTableEntityBlock;
import com.tutorial.lively_danmaku.BlockEntity.ReimuEntityBlock;
import com.tutorial.lively_danmaku.BlockEntity.render.ReimuTileEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityTypeRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, "lively_danmaku");
    public static final RegistryObject<BlockEntityType<ReimuEntityBlock>> REIMU_ENTITY_BLOCK = BLOCK_ENTITY_TYPES.register("reimu_block",
            () -> BlockEntityType.Builder.of(ReimuEntityBlock::new, BlockRegistry.REIMU_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<DanmakuTableEntityBlock>> DANMAKU_TABLE_ENTITY_BLOCK = BLOCK_ENTITY_TYPES.register("danmaku_table",
            () -> BlockEntityType.Builder.of(DanmakuTableEntityBlock::new, BlockRegistry.DANMAKU_TABLE.get()).build(null));
    @OnlyIn(Dist.CLIENT)
    public static void registerTileEntityRenders() {
        BlockEntityRenderers.register(REIMU_ENTITY_BLOCK.get(),ReimuTileEntityRenderer::new);
    }
}