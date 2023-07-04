package com.tutorial.crystal.Entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.tutorial.crystal.Entity.Danmaku;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class DanmakuRender extends EntityRenderer<Danmaku> {
    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("crystal", "textures/entity/danmaku.png");
    private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(TEXTURE_LOCATION);
    public DanmakuRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Danmaku entity) {
        return TEXTURE_LOCATION;
    }
    @Override
    public void render(@NotNull Danmaku danmaku, float p_114081_, float p_114082_, PoseStack poseStack, MultiBufferSource p_114084_, int p_114085_) {
        poseStack.pushPose();
        poseStack.scale(1.0F, 1.0F, 1.0F);
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        PoseStack.Pose posestack$pose = poseStack.last();
        Matrix4f matrix4f = posestack$pose.pose();
        Matrix3f matrix3f = posestack$pose.normal();
        VertexConsumer vertexconsumer = p_114084_.getBuffer(RENDER_TYPE);
        renderSquare(vertexconsumer, matrix4f, matrix3f, p_114085_,danmaku.getSize());
        poseStack.popPose();
        super.render(danmaku, p_114081_, p_114082_, poseStack, p_114084_, p_114085_);
    }

    private static void renderSquare(VertexConsumer consumer, Matrix4f matrix4f, Matrix3f matrix3f, int p_253829_, float size) {
        float x1 = -size;
        float y1 = -size;
        consumer.vertex(matrix4f, x1, y1, 0.0F)
                .color(255, 255, 255, 255)
                .uv(0.0F, 0.0F)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(p_253829_)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .endVertex();

        consumer.vertex(matrix4f, size, y1, 0.0F)
                .color(255, 255, 255, 255)
                .uv(1.0F, 0.0F)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(p_253829_)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .endVertex();

        consumer.vertex(matrix4f, size, size, 0.0F)
                .color(255, 255, 255, 255)
                .uv(1.0F, 1.0F)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(p_253829_)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .endVertex();

        consumer.vertex(matrix4f, x1, size, 0.0F)
                .color(255, 255, 255, 255)
                .uv(0.0F, 1.0F)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(p_253829_)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .endVertex();
    }

}
