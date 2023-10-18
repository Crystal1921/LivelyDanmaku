package com.tutorial.lively_danmaku.mixin.mixinClass;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.tutorial.lively_danmaku.mixin.mixinInterface.GuiGraphicsInterface;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.FastColor;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GuiGraphics.class)
public abstract class MixinGuiGraphics implements GuiGraphicsInterface {

    @Shadow @Final private PoseStack pose;

    @Shadow @Final private MultiBufferSource.BufferSource bufferSource;

    @Shadow @Deprecated protected abstract void flushIfUnmanaged();

    @Override
    public void livelyDanmaku$drawLine(int x1, int y1, int x2, int y2, int pZ, int pColor, int width) {
        Matrix4f matrix4f = this.pose.last().pose();

        float f3 = (float) FastColor.ARGB32.alpha(pColor) / 255.0F;
        float f = (float)FastColor.ARGB32.red(pColor) / 255.0F;
        float f1 = (float)FastColor.ARGB32.green(pColor) / 255.0F;
        float f2 = (float)FastColor.ARGB32.blue(pColor) / 255.0F;

        VertexConsumer vertexconsumer = this.bufferSource.getBuffer(RenderType.gui());
        double angle = Math.atan2(y2 - y1, x2 - x1);
        double halfWidth = width / 2.0;

        double xOffset = Math.sin(angle) * halfWidth;
        double yOffset = Math.cos(angle) * halfWidth;

        double x1a = x1 - xOffset;
        double y1a = y1 + yOffset;
        double x1b = x1 + xOffset;
        double y1b = y1 - yOffset;
        double x2a = x2 - xOffset;
        double y2a = y2 + yOffset;
        double x2b = x2 + xOffset;
        double y2b = y2 - yOffset;

        vertexconsumer.vertex(matrix4f, (float)x1a, (float)y1a, (float)pZ).color(f, f1, f2, f3).endVertex();
        vertexconsumer.vertex(matrix4f, (float)x2a, (float)y2a, (float)pZ).color(f, f1, f2, f3).endVertex();
        vertexconsumer.vertex(matrix4f, (float)x2b, (float)y2b, (float)pZ).color(f, f1, f2, f3).endVertex();
        vertexconsumer.vertex(matrix4f, (float)x1b, (float)y1b, (float)pZ).color(f, f1, f2, f3).endVertex();

        this.flushIfUnmanaged();
    }
}
