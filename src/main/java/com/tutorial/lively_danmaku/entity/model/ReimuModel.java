package com.tutorial.lively_danmaku.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.tutorial.lively_danmaku.entity.Reimu;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import org.jetbrains.annotations.NotNull;

public class ReimuModel extends EntityModel<Reimu> {
    public ReimuModel(ModelPart root) {
        super(RenderType::entityCutoutNoCull);
        this.head = root.getChild("head");
        this.body1 = root.getChild("body1");
        this.body2 = root.getChild("body2");
        this.body3 = root.getChild("body3");
        this.footL = root.getChild("footL");
        this.footR = root.getChild("footR");
        this.handL = root.getChild("handL");
        setRotation(handL, 0F, 0F, -0.3490659F);
        this.handR = root.getChild("handR");
        setRotation(handR, 0F, 0F, 0.3490659F);
        this.headdreesM = root.getChild("headdressM");
        this.hair = root.getChild("hair");
        setRotation(hair, 0.1745329F, 0F, -0.2617994F);
        this.hair2 = root.getChild("hair2");
        setRotation(hair2, 0.3490659F, 0F, 0F);
        this.headdressRT = root.getChild("headdressRT");
        setRotation(headdressRT, 0F, 0F, 0.1745329F);
        this.headdressRB = root.getChild("headdressRB");
        setRotation(headdressRB, 0F, 0F, 0.6981317F);
        this.headdressLT = root.getChild("headdressLT");
        setRotation(headdressLT, 0F, 0F, -0.1745329F);
        this.headdressLB = root.getChild("headdressLB");
        setRotation(headdressLB, 0F, 0F, -0.6981317F);
    }

    private final ModelPart head,body1,body2,body3,footL,footR,handR,handL,headdreesM,hair,headdressLT,headdressRT,headdressLB,headdressRB,hair2;

    public static LayerDefinition create() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition partRoot = mesh.getRoot();

        partRoot.addOrReplaceChild("head", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-4F, 0F, -4F, 8, 8, 8)
                        .mirror(),
                PartPose.offset(0F, 0F, 0F));
        partRoot.addOrReplaceChild("body1", CubeListBuilder.create()
                        .texOffs(32,0)
                        .addBox(-3F, 0F, -3F, 6, 4, 6),
                PartPose.offset(0,8,0));
        partRoot.addOrReplaceChild("body2", CubeListBuilder.create()
                        .texOffs(0,16)
                        .addBox(-4F, 0F, -4F, 8, 3, 8),
                PartPose.offset(0,12,0));
        partRoot.addOrReplaceChild("body3", CubeListBuilder.create()
                        .texOffs(56,0)
                        .addBox(-5F, 0F, -5F, 10, 6, 10),
                PartPose.offset(0,15,0));
        partRoot.addOrReplaceChild("footL", CubeListBuilder.create()
                        .texOffs(0,27)
                        .addBox(0F, 0F, -1F, 2, 3, 2),
                PartPose.offset(1,21,0));
        partRoot.addOrReplaceChild("footR", CubeListBuilder.create()
                        .texOffs(0,27)
                        .addBox(0F, 0F, -1F, 2, 3, 2),
                PartPose.offset(-2,21,0));
        partRoot.addOrReplaceChild("handL", CubeListBuilder.create()
                        .texOffs(96,0)
                        .addBox(-1F, 0F, -1F, 2, 9, 2),
                PartPose.offset(4,8,0));
        partRoot.addOrReplaceChild("handR", CubeListBuilder.create()
                        .texOffs(96,0)
                        .addBox(-1F, 0F, -1F, 2, 9, 2),
                PartPose.offset(-4,8,0));
        partRoot.addOrReplaceChild("headdressM", CubeListBuilder.create()
                        .texOffs(32,16)
                        .addBox(-1F, -1F, 0F, 2, 2, 3),
                PartPose.offset(0,0,4));
        partRoot.addOrReplaceChild("hair", CubeListBuilder.create()
                        .texOffs(52,17)
                        .addBox(0F, 0F, 0F, 2, 9, 1),
                PartPose.offset(-1,0,5));
        partRoot.addOrReplaceChild("hair2", CubeListBuilder.create()
                        .texOffs(58,18)
                        .addBox(-4F, 0F, 0F, 8, 9, 0),
                PartPose.offset(0,8,4));
        partRoot.addOrReplaceChild("headdressRB", CubeListBuilder.create()
                        .texOffs(42,11)
                        .addBox(-2F, 0F, 0F, 4, 9, 1),
                PartPose.offset(-1,1,4));
        partRoot.addOrReplaceChild("headdressLB", CubeListBuilder.create()
                        .texOffs(42,11)
                        .addBox(-2F, 0F, 0F, 4, 9, 1),
                PartPose.offset(1,1,4));
        partRoot.addOrReplaceChild("headdressRT", CubeListBuilder.create()
                        .texOffs(32,26)
                        .addBox(-9F, -2F, 0F, 8, 4, 1),
                PartPose.offset(0,0,4));
        partRoot.addOrReplaceChild("headdressLT", CubeListBuilder.create()
                        .texOffs(32,21)
                        .addBox(0F, -2F, 0F, 8, 4, 1),
                PartPose.offset(1,0,4));
        return LayerDefinition.create(mesh,128,32);
    }
    @Override
    public void renderToBuffer(@NotNull PoseStack ms, @NotNull VertexConsumer buffer, int light, int overlay, float r, float g, float b, float a) {
        this.head.render(ms, buffer, light, overlay, r, g, b, a);
        this.body1.render(ms, buffer, light, overlay, r, g, b, a);
        this.body2.render(ms, buffer, light, overlay, r, g, b, a);
        this.body3.render(ms, buffer, light, overlay, r, g, b, a);
        this.footR.render(ms, buffer, light, overlay, r, g, b, a);
        this.footL.render(ms, buffer, light, overlay, r, g, b, a);
        this.handL.render(ms, buffer, light, overlay, r, g, b, a);
        this.handR.render(ms, buffer, light, overlay, r, g, b, a);
        this.headdreesM.render(ms, buffer, light, overlay, r, g, b, a);
        this.hair.render(ms, buffer, light, overlay, r, g, b, a);
        this.hair2.render(ms, buffer, light, overlay, r, g, b, a);
        this.headdressRB.render(ms, buffer, light, overlay, r, g, b, a);
        this.headdressLB.render(ms, buffer, light, overlay, r, g, b, a);
        this.headdressRT.render(ms, buffer, light, overlay, r, g, b, a);
        this.headdressLT.render(ms, buffer, light, overlay, r, g, b, a);
    }

    @Override
    public void setupAnim(@NotNull Reimu reimu, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
        // 头部转动
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180f);

        // 左脚右脚，左手右手的运动
        this.footL.xRot = (float) (Math.cos(limbSwing * 2F) * 1.2F * limbSwingAmount);
        this.footR.xRot = (float) (-Math.cos(limbSwing * 2F) * 1.2F * limbSwingAmount);
        this.handL.xRot = (float) (Math.cos(limbSwing * 1.2F) * 0.8F * limbSwingAmount);
        this.handL.zRot = (float) (Math.cos(ageInTicks * 0.05f) * 0.05f - 0.523f);
        this.handR.xRot = (float) (-Math.cos(limbSwing * 1.2F) * 0.8F * limbSwingAmount);
        this.handR.zRot = (float) (-Math.cos(ageInTicks * 0.05f) * 0.05f + 0.523f);

        // 头饰的周期运动
        this.headdressLB.yRot = (float) (Math.cos(ageInTicks * 0.2f) * 0.05f - 0.2f);
        this.headdressLT.yRot = (float) (Math.cos(ageInTicks * 0.2f) * 0.05f - 0.2f);
        double v = -Math.cos(ageInTicks * 0.2f) * 0.05f + 0.2f;
        this.headdressRB.yRot = (float) v;
        this.headdressRT.yRot = (float) v;
    }
    private void setRotation(ModelPart model, float x, float y, float z) {
        model.setRotation(x,y,z);
    }
}
