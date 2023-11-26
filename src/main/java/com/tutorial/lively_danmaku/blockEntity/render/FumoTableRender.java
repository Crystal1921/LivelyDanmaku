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
    public void render(@NotNull FumoTableTE fumoTable, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        AtomicReference<ItemStack> itemStack = new AtomicReference<>(ItemStack.EMPTY);
        fumoTable.getCapability(ForgeCapabilities.ITEM_HANDLER)
                .ifPresent(iItemHandler -> itemStack.set(iItemHandler.getStackInSlot(0)));
        BakedModel bakedmodel = itemRenderer.getModel(itemStack.get(), fumoTable.getLevel(), null, 1);
        float tick = Objects.requireNonNull(fumoTable.getLevel()).getGameTime() * 4;
        ms.pushPose();
        ms.translate(0.5, 0.35 + Math.sin(Math.toRadians((double) tick * 0.5)) * 0.15,0.5);
        ms.mulPose(Axis.YP.rotationDegrees(tick));
        itemRenderer.render(itemStack.get(), ItemDisplayContext.GROUND, false, ms, buffer, LightTexture.pack(15, 15), OverlayTexture.NO_OVERLAY, bakedmodel);
        ms.popPose();
    }
}
