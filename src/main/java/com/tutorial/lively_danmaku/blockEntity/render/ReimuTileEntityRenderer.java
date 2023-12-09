package com.tutorial.lively_danmaku.blockEntity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.tutorial.lively_danmaku.blockEntity.ReimuEntityTE;
import com.tutorial.lively_danmaku.entity.model.ReimuModel;
import com.tutorial.lively_danmaku.init.ModelLayersRegistry;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import static com.tutorial.lively_danmaku.block.ReimuBlock.FACING;

public class ReimuTileEntityRenderer implements BlockEntityRenderer<ReimuEntityTE> {
    private final ReimuModel reimuModel;
    public ReimuTileEntityRenderer(BlockEntityRendererProvider.Context renderer) {
        this.reimuModel = new ReimuModel(renderer.bakeLayer(ModelLayersRegistry.REIMU));
    }

    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("lively_danmaku", "textures/entity/reimu.png");

    @Override
    public void render(@NotNull ReimuEntityTE reimuEntityBlock, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        Direction direction = reimuEntityBlock.getBlockState().getValue(FACING);
        VertexConsumer builder = buffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE_LOCATION));
        ms.pushPose();
        ms.mulPose(Axis.ZP.rotationDegrees(180));
        ms.translate(-0.5,-1.5,0.5);
        switch (direction) {
            case EAST -> ms.mulPose(Axis.YP.rotationDegrees(90));
            case SOUTH -> ms.mulPose(Axis.YP.rotationDegrees(180));
            case WEST -> ms.mulPose(Axis.YP.rotationDegrees(270));
        }
        this.reimuModel.renderToBuffer(ms, builder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        ms.popPose();
    }
}
