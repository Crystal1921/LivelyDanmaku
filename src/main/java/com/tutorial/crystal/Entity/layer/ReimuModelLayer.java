package com.tutorial.crystal.Entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.tutorial.crystal.Entity.Reimu;
import com.tutorial.crystal.Entity.model.ReimuModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class ReimuModelLayer extends RenderLayer<Reimu, ReimuModel<Reimu>> {
    private final ItemInHandRenderer itemInHandRenderer;

    public ReimuModelLayer(RenderLayerParent<Reimu, ReimuModel<Reimu>> reimu, ItemInHandRenderer item) {
        super(reimu);
        this.itemInHandRenderer = item;
    }
    @Override
    public void render(PoseStack pose, MultiBufferSource source, int p_117351_, Reimu reimu, float p_117353_, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_) {
        ItemStack itemstack = reimu.getItemBySlot(EquipmentSlot.MAINHAND);
        pose.pushPose();
        pose.translate(-0.5,0.9,-0.1);
        pose.mulPose(Axis.YP.rotationDegrees(180));
        pose.mulPose(Axis.XP.rotationDegrees(135));
        pose.mulPose(Axis.ZP.rotationDegrees((float) Math.toDegrees(0.3490659F)));
        this.itemInHandRenderer.renderItem(reimu, itemstack, ItemDisplayContext.GROUND, false, pose, source, p_117351_);
        pose.popPose(); // 确保在渲染完成后恢复矩阵状态
    }

}
