package com.tutorial.crystal.BlockEntity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.tutorial.crystal.BlockEntity.ReimuEntityBlock;
import com.tutorial.crystal.Entity.model.ReimuModel;
import com.tutorial.crystal.init.ModelLayersRegistry;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class ReimuTileEntityRenderer implements BlockEntityRenderer<ReimuEntityBlock> {
    private final ReimuModel reimuModel;
    public ReimuTileEntityRenderer(BlockEntityRendererProvider.Context renderer) {
        this.reimuModel = new ReimuModel(renderer.bakeLayer(ModelLayersRegistry.REIMU));
    }

    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("crystal", "textures/entity/reimu.png");

    @Override
    public void render(ReimuEntityBlock reimuEntityBlock, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        VertexConsumer builder = buffer.getBuffer(this.reimuModel.renderType(TEXTURE_LOCATION));
        ms.pushPose();
        ms.mulPose(Axis.ZP.rotationDegrees(180));
        ms.translate(-0.5,-1.5,0.5);
        this.reimuModel.renderToBuffer(ms, builder, 0xF000F0, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        ms.popPose();
    }
}
