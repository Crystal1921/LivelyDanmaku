package com.tutorial.crystal.Entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.tutorial.crystal.Entity.StarDanmaku;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class StarDanmakuRender extends EntityRenderer<StarDanmaku> {
    ResourceLocation TEXTURE_LOCATION = new ResourceLocation("crystal", "textures/entity/star.png");
    private static final RenderType[] RENDER_TYPE = {
            RenderType.entityCutoutNoCull(new ResourceLocation("crystal", "textures/entity/star.png")),
            RenderType.entityCutoutNoCull(new ResourceLocation("crystal","textures/entity/star1.png")),
            RenderType.entityCutoutNoCull(new ResourceLocation("crystal","textures/entity/star2.png"))
    };
    public StarDanmakuRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull StarDanmaku entity) {
        return TEXTURE_LOCATION;
    }
    @Override
    public void render(@NotNull StarDanmaku danmaku, float p_114081_, float p_114082_, PoseStack poseStack, MultiBufferSource source, int p_114085_) {
        int color = danmaku.getColor();
        poseStack.pushPose();
        poseStack.scale(1.0F, 1.0F, 1.0F);
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        PoseStack.Pose posestack$pose = poseStack.last();
        Matrix4f matrix4f = posestack$pose.pose();
        Matrix3f matrix3f = posestack$pose.normal();
        VertexConsumer vertexconsumer = source.getBuffer(RENDER_TYPE[color]);
        vertex(vertexconsumer, matrix4f, matrix3f, p_114085_, 0.0F, 0, 0, 1);
        vertex(vertexconsumer, matrix4f, matrix3f, p_114085_, 1.0F, 0, 1, 1);
        vertex(vertexconsumer, matrix4f, matrix3f, p_114085_, 1.0F, 1, 1, 0);
        vertex(vertexconsumer, matrix4f, matrix3f, p_114085_, 0.0F, 1, 0, 0);
        poseStack.popPose();
        super.render(danmaku, p_114081_, p_114082_, poseStack, source, p_114085_);
    }
    private static void vertex(VertexConsumer p_254095_, Matrix4f p_254477_, Matrix3f p_253948_, int p_253829_, float p_253995_, int p_254031_, int p_253641_, int p_254243_) {
        p_254095_.vertex(p_254477_, p_253995_ - 0.5F, (float)p_254031_ - 0.25F, 0.0F).color(255, 255, 255, 255).uv((float)p_253641_, (float)p_254243_).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(p_253829_).normal(p_253948_, 0.0F, 1.0F, 0.0F).endVertex();
    }
}
