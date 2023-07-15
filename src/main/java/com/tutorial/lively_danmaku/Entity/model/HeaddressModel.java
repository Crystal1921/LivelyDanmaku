package com.tutorial.lively_danmaku.Entity.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class HeaddressModel extends HumanoidModel {
    public HeaddressModel(ModelPart modelPart) {
        super(modelPart);
    }

    public static MeshDefinition setup(CubeDeformation deformation) {
        MeshDefinition meshdefinition = HumanoidModel.createMesh(deformation, 0);
        PartDefinition partdefinition = meshdefinition.getRoot();

        var head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(32, 0)
                        .addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, deformation),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        head.addOrReplaceChild("headdressM", CubeListBuilder.create()
                .texOffs(0,5)
                .addBox(-1F, -1F, 0F, 2, 2, 3),
                PartPose.offset(0,-6,4));
        head.addOrReplaceChild("headdressRT", CubeListBuilder.create()
                .texOffs(0,10)
                .addBox(-9F, -2F, 0F, 8, 4, 1),
                PartPose.offsetAndRotation(0,-6,4,0F, 0F, 0.1745329F));
        head.addOrReplaceChild("headdressLT", CubeListBuilder.create()
                .texOffs(0,15)
                .addBox(0F, -2F, 0F, 8, 4, 1),
                PartPose.offsetAndRotation(1,-6,4,0F, 0F, -0.1745329F));
        head.addOrReplaceChild("headdressRB", CubeListBuilder.create()
                        .texOffs(10,0)
                        .addBox(-2F, 0F, 0F, 4, 9, 1),
                PartPose.offsetAndRotation(-1,-5,4,0F, 0F, 0.6981317F));
        head.addOrReplaceChild("headdressLB", CubeListBuilder.create()
                        .texOffs(10,0)
                        .addBox(-2F, 0F, 0F, 4, 9, 1),
                PartPose.offsetAndRotation(1,-5,4,0F, 0F, -0.6981317F));
        var rightArm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create()
                        .texOffs(40, 16)
                        .addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation),
                PartPose.offset(-5.0F, 2.0F, 0.0F));

        var leftArm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create()
                        .texOffs(40, 16).mirror()
                        .addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation),
                PartPose.offset(5.0F, 2.0F, 0.0F));
        var rightLeg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create()
                        .texOffs(0, 16)
                        .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation),
                PartPose.offset(-1.9F, 12.0F, 0.0F));

        var leftLeg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create()
                        .texOffs(0, 16).mirror()
                        .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation),
                PartPose.offset(1.9F, 12.0F, 0.0F));
        return meshdefinition;
    }
}
