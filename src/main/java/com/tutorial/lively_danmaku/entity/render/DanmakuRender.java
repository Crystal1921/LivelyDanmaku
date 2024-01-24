package com.tutorial.lively_danmaku.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.tutorial.lively_danmaku.entity.NormalDanmaku;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.awt.*;

public class DanmakuRender extends EntityRenderer<NormalDanmaku> {
    public static final ResourceLocation TEXTURE_IN = new ResourceLocation("lively_danmaku", "textures/entity/danmaku_in.png");
    public static final ResourceLocation TEXTURE_OUT = new ResourceLocation("lively_danmaku", "textures/entity/danmaku_out.png");
    private static final RenderType RENDER_TYPE_IN = RenderType.entityCutoutNoCull(TEXTURE_IN);
    private static final RenderType RENDER_TYPE_OUT = RenderType.entityCutoutNoCull(TEXTURE_OUT);
    public DanmakuRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull NormalDanmaku entity) {
        return TEXTURE_IN;
    }
    @Override
    public void render(@NotNull NormalDanmaku danmaku, float pEntityYaw, float pPartialTick, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight) {
        poseStack.pushPose();
        poseStack.scale(1.0F, 1.0F, 1.0F);
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        PoseStack.Pose posestack$pose = poseStack.last();
        Matrix4f matrix4f = posestack$pose.pose();
        Matrix3f matrix3f = posestack$pose.normal();
        VertexConsumer vertexConsumer1 = pBuffer.getBuffer(RENDER_TYPE_IN);
        renderSquare(vertexConsumer1, matrix4f, matrix3f, pPackedLight, danmaku.getSize(), Color.white.getRGB());
        VertexConsumer vertexConsumer2 = pBuffer.getBuffer(RENDER_TYPE_OUT);
        renderSquare(vertexConsumer2, matrix4f, matrix3f, pPackedLight, danmaku.getSize(), danmaku.getColor());
        poseStack.popPose();
        super.render(danmaku, pEntityYaw, pPartialTick, poseStack, pBuffer, pPackedLight);
    }

    private static void renderSquare(VertexConsumer consumer, Matrix4f matrix4f, Matrix3f matrix3f, int i, float size,int rgb) {
        float x1 = -size;
        float y1 = -size;
        Color color = new Color(rgb);
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        int alpha = 255;
        consumer.vertex(matrix4f, x1, y1, 0.0F)
                .color(red, green, blue, alpha)
                .uv(0.0F, 0.0F)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(i)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .endVertex();

        consumer.vertex(matrix4f, size, y1, 0.0F)
                .color(red, green, blue, alpha)
                .uv(1.0F, 0.0F)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(i)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .endVertex();

        consumer.vertex(matrix4f, size, size, 0.0F)
                .color(red, green, blue, alpha)
                .uv(1.0F, 1.0F)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(i)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .endVertex();

        consumer.vertex(matrix4f, x1, size, 0.0F)
                .color(red, green, blue, alpha)
                .uv(0.0F, 1.0F)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(i)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .endVertex();
    }

}
