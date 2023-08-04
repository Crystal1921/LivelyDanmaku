package com.tutorial.lively_danmaku.Entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.tutorial.lively_danmaku.Entity.Hakurei_bullet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public class HakureiBulletRender extends EntityRenderer<Hakurei_bullet> {
    private final Quaternionf rotation = new Quaternionf(0.0F, 0.0F, 0.0F, 1.0F);
    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("lively_danmaku", "textures/entity/hakurei_danmaku.png");
    private static final RenderType[] RENDER_TYPE = {
            RenderType.entityCutoutNoCull(new ResourceLocation("lively_danmaku", "textures/entity/hakurei_danmaku.png")),
            RenderType.entityCutoutNoCull(new ResourceLocation("lively_danmaku","textures/entity/hakurei_danmaku1.png")),
            RenderType.entityCutoutNoCull(new ResourceLocation("lively_danmaku","textures/entity/hakurei_danmaku2.png"))
    };
    public HakureiBulletRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Hakurei_bullet entity) {
        return TEXTURE_LOCATION;
    }
    @Override
    public void render(@NotNull Hakurei_bullet danmaku, float p_114081_, float p_114082_, PoseStack poseStack, MultiBufferSource bufferSource, int i) {
        rotation.rotationYXZ((float) Math.toRadians(danmaku.getYRot()),(float) Math.toRadians(-danmaku.getXRot() + 90),0);
        poseStack.pushPose();
        poseStack.scale(1.0F, 1.0F, 1.0F);
        poseStack.mulPose(rotation);
        PoseStack.Pose posestack$pose = poseStack.last();
        Matrix4f matrix4f = posestack$pose.pose();
        Matrix3f matrix3f = posestack$pose.normal();
        VertexConsumer vertexconsumer = bufferSource.getBuffer(RENDER_TYPE[danmaku.getEntityData().get(Hakurei_bullet.BULLET_STAGE)]);
        vertex(vertexconsumer, matrix4f, matrix3f, i, 0.0F, 0, 0, 1);
        vertex(vertexconsumer, matrix4f, matrix3f, i, 0.5F, 0, 1, 1);
        vertex(vertexconsumer, matrix4f, matrix3f, i, 0.5F, 1, 1, 0);
        vertex(vertexconsumer, matrix4f, matrix3f, i, 0.0F, 1, 0, 0);
        poseStack.popPose();
        super.render(danmaku, p_114081_, p_114082_, poseStack, bufferSource, i);
    }
    private static void vertex(VertexConsumer consumer, Matrix4f matrix4f, Matrix3f matrix3f, int p_253829_, float p_253995_, int p_254031_, int p_253641_, int p_254243_) {
        consumer.vertex(matrix4f, p_253995_ - 0.25F, (float)p_254031_ - 0.25F, 0.0F)
                .color(255, 255, 255, 255)
                .uv((float)p_253641_, (float)p_254243_)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(p_253829_)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .endVertex();
    }
}
