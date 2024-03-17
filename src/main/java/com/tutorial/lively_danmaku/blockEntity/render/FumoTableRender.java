package com.tutorial.lively_danmaku.blockEntity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.tutorial.lively_danmaku.blockEntity.FumoTableTE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class FumoTableRender implements BlockEntityRenderer<FumoTableTE> {
    public FumoTableRender(BlockEntityRendererProvider.Context renderer) {
    }
    @Override
    public void render(@NotNull FumoTableTE pBlockEntity, float pPartialTick, PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int light, int pPackedOverlay) {
        ItemStack theItem = pBlockEntity.theItem;
        if (!theItem.isEmpty()) {
            float rotation = pBlockEntity.getLevel().getGameTime() % 360 + pPartialTick;
            pPoseStack.pushPose();
            pPoseStack.translate(0.5, 1, 0.5);
            pPoseStack.scale(0.625F, 0.625F, 0.625F);
            pPoseStack.mulPose(Axis.YP.rotationDegrees(rotation * 2));
            Minecraft.getInstance().getItemRenderer().renderStatic(pBlockEntity.theItem, ItemDisplayContext.FIXED, LightTexture.FULL_BRIGHT, pPackedOverlay, pPoseStack, pBuffer, pBlockEntity.getLevel (), (int) pBlockEntity.getBlockPos().asLong());
            pPoseStack.popPose();
        }
    }
}
