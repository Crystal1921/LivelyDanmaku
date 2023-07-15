package com.tutorial.lively_danmaku.Entity.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.mojang.blaze3d.vertex.PoseStack;
import com.tutorial.lively_danmaku.Entity.player;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import org.jetbrains.annotations.NotNull;

public class PlayerModel<T extends player> extends HumanoidModel<T> {
    public final ModelPart leftSleeve;
    public final ModelPart rightSleeve;
    public final ModelPart leftPants;
    public final ModelPart rightPants;
    public final ModelPart jacket;
    private final ModelPart cloak;
    private final ModelPart ear;
    private final boolean slim;
    public PlayerModel(ModelPart modelPart, boolean slim) {
        super(modelPart);
        this.slim = slim;
        this.ear = modelPart.getChild("ear");
        this.cloak = modelPart.getChild("cloak");
        this.leftSleeve = modelPart.getChild("left_sleeve");
        this.rightSleeve = modelPart.getChild("right_sleeve");
        this.leftPants = modelPart.getChild("left_pants");
        this.rightPants = modelPart.getChild("right_pants");
        this.jacket = modelPart.getChild("jacket");
    }
    public static MeshDefinition createMesh(CubeDeformation deformation, boolean isSlim) {
        MeshDefinition meshdefinition = HumanoidModel.createMesh(deformation, 0.0F);
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("ear", CubeListBuilder.create().texOffs(24, 0).addBox(-3.0F, -6.0F, -1.0F, 6.0F, 6.0F, 1.0F, deformation), PartPose.ZERO);
        partdefinition.addOrReplaceChild("cloak", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, 0.0F, -1.0F, 10.0F, 16.0F, 1.0F, deformation, 1.0F, 0.5F), PartPose.offset(0.0F, 0.0F, 0.0F));
        if (isSlim) {
            partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, deformation), PartPose.offset(5.0F, 2.5F, 0.0F));
            partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, deformation), PartPose.offset(-5.0F, 2.5F, 0.0F));
            partdefinition.addOrReplaceChild("left_sleeve", CubeListBuilder.create().texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, deformation.extend(0.25F)), PartPose.offset(5.0F, 2.5F, 0.0F));
            partdefinition.addOrReplaceChild("right_sleeve", CubeListBuilder.create().texOffs(40, 32).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, deformation.extend(0.25F)), PartPose.offset(-5.0F, 2.5F, 0.0F));
        } else {
            partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation), PartPose.offset(5.0F, 2.0F, 0.0F));
            partdefinition.addOrReplaceChild("left_sleeve", CubeListBuilder.create().texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation.extend(0.25F)), PartPose.offset(5.0F, 2.0F, 0.0F));
            partdefinition.addOrReplaceChild("right_sleeve", CubeListBuilder.create().texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation.extend(0.25F)), PartPose.offset(-5.0F, 2.0F, 0.0F));
        }

        partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation), PartPose.offset(1.9F, 12.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_pants", CubeListBuilder.create().texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation.extend(0.25F)), PartPose.offset(1.9F, 12.0F, 0.0F));
        partdefinition.addOrReplaceChild("right_pants", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation.extend(0.25F)), PartPose.offset(-1.9F, 12.0F, 0.0F));
        partdefinition.addOrReplaceChild("jacket", CubeListBuilder.create().texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, deformation.extend(0.25F)), PartPose.ZERO);
        return meshdefinition;
    }

    protected @NotNull Iterable<ModelPart> bodyParts() {
        return Iterables.concat(super.bodyParts(), ImmutableList.of(this.leftPants, this.rightPants, this.leftSleeve, this.rightSleeve, this.jacket));
    }

    public void setupAnim(@NotNull T p_103395_, float p_103396_, float p_103397_, float p_103398_, float p_103399_, float p_103400_) {
        super.setupAnim(p_103395_, p_103396_, p_103397_, p_103398_, p_103399_, p_103400_);
        this.leftPants.copyFrom(this.leftLeg);
        this.rightPants.copyFrom(this.rightLeg);
        this.leftSleeve.copyFrom(this.leftArm);
        this.rightSleeve.copyFrom(this.rightArm);
        this.jacket.copyFrom(this.body);
        if (p_103395_.getItemBySlot(EquipmentSlot.CHEST).isEmpty()) {
            if (p_103395_.isCrouching()) {
                this.cloak.z = 1.4F;
                this.cloak.y = 1.85F;
            } else {
                this.cloak.z = 0.0F;
                this.cloak.y = 0.0F;
            }
        } else if (p_103395_.isCrouching()) {
            this.cloak.z = 0.3F;
            this.cloak.y = 0.8F;
        } else {
            this.cloak.z = -1.1F;
            this.cloak.y = -0.85F;
        }

    }

    public void setAllVisible(boolean allVisible) {
        super.setAllVisible(allVisible);
        this.leftSleeve.visible = allVisible;
        this.rightSleeve.visible = allVisible;
        this.leftPants.visible = allVisible;
        this.rightPants.visible = allVisible;
        this.jacket.visible = allVisible;
        this.cloak.visible = allVisible;
        this.ear.visible = allVisible;
    }

    public void translateToHand(@NotNull HumanoidArm arm, @NotNull PoseStack poseStack) {
        ModelPart modelpart = this.getArm(arm);
        if (this.slim) {
            float f = 0.5F * (float)(arm == HumanoidArm.RIGHT ? 1 : -1);
            modelpart.x += f;
            modelpart.translateAndRotate(poseStack);
            modelpart.x -= f;
        } else {
            modelpart.translateAndRotate(poseStack);
        }

    }

}
