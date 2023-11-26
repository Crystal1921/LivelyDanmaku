package com.tutorial.lively_danmaku.entity.model;// Made with Blockbench 4.8.0
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;


public class BroomstickModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "broomstickmodel"), "main");
	private final ModelPart broom;

	public BroomstickModel(ModelPart root) {
		this.broom = root.getChild("broom");
	}

	public static LayerDefinition create() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition broom = partdefinition.addOrReplaceChild("broom", CubeListBuilder.create().texOffs(0, 0).addBox(-10.5F, -4.75F, 6.5F, 1.5F, 1.5F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(14, 11).addBox(-11.925F, -5.75F, 12.1F, 4.25F, 3.5F, 0.75F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-11.0F, -5.375F, 8.25F, 2.5F, 2.75F, 1.25F, new CubeDeformation(0.005F))
		.texOffs(13, 0).addBox(-11.0F, -5.25F, 12.9566F, 2.5F, 2.5F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-10.25F, -4.5F, -15.5F, 1.0F, 1.0F, 31.0F, new CubeDeformation(0.0F)), PartPose.offset(9.75F, 21.0F, -1.0F));

		PartDefinition cube_r1 = broom.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(16, 16).addBox(-1.0F, -1.5F, -1.75F, 2.5F, 2.5F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.0F, -3.0F, 10.5F, 0.3927F, 0.0F, 0.0F));

		PartDefinition cube_r2 = broom.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(33, 0).addBox(-1.0F, -1.0F, -1.75F, 2.5F, 2.5F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.0F, -5.0F, 10.5F, -0.3927F, 0.0F, 0.0F));

		PartDefinition cube_r3 = broom.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 32).addBox(-1.25F, -0.576F, -2.4665F, 2.5F, 3.5F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.75F, -4.0F, 15.5981F, -0.5236F, 0.0F, 0.0F));

		PartDefinition cube_r4 = broom.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(18, 32).addBox(-1.25F, -2.924F, -2.4665F, 2.5F, 3.5F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.75F, -4.0F, 15.5981F, 0.5236F, 0.0F, 0.0F));

		PartDefinition cube_r5 = broom.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 11).addBox(-2.0F, -1.25F, 0.0F, 3.5F, 2.5F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.25F, -4.0F, 13.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition cube_r6 = broom.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 20).addBox(-1.5F, -1.25F, 0.0F, 3.5F, 2.5F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.25F, -4.0F, 13.0F, 0.0F, 0.5236F, 0.0F));

		PartDefinition cube_r7 = broom.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(33, 7).addBox(-1.5F, -1.5F, -2.25F, 2.5F, 2.5F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.75F, -3.75F, 11.5F, 0.0F, -0.3927F, 0.0F));

		PartDefinition cube_r8 = broom.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(33, 14).addBox(-1.0F, -1.5F, -2.25F, 2.5F, 2.5F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.75F, -3.75F, 11.5F, 0.0F, 0.3927F, 0.0F));

		PartDefinition cube_r9 = broom.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 4).addBox(-0.75F, -0.75F, -0.75F, 1.5F, 1.5F, 1.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.75F, -4.0F, -16.25F, 0.0F, 0.7854F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		broom.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}