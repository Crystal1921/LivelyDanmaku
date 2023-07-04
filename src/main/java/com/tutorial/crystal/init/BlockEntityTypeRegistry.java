package com.tutorial.crystal.init;

import com.tutorial.crystal.BlockEntity.ReimuEntityBlock;
import com.tutorial.crystal.BlockEntity.render.ReimuTileEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityTypeRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, "crystal");
    public static final RegistryObject<BlockEntityType<ReimuEntityBlock>> REIMU_ENTITY_BLOCK = BLOCK_ENTITY_TYPES.register("reimu_block",
            () -> BlockEntityType.Builder.of(ReimuEntityBlock::new, BlockRegistry.REIMU_BLOCK.get()).build(null));
    @OnlyIn(Dist.CLIENT)
    public static void registerTileEntityRenders() {
        BlockEntityRenderers.register(REIMU_ENTITY_BLOCK.get(),ReimuTileEntityRenderer::new);
    }
}
