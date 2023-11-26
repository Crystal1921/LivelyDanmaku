package com.tutorial.lively_danmaku.entity.render;

import com.tutorial.lively_danmaku.entity.Broomstick;
import com.tutorial.lively_danmaku.entity.model.BroomstickModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class BroomstickRender extends MobRenderer<Broomstick, BroomstickModel<Broomstick>> {
    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("lively_danmaku", "textures/entity/broomstick.png");
    private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(TEXTURE_LOCATION);
    public BroomstickRender(EntityRendererProvider.Context manager, BroomstickModel<Broomstick> model, float shadowSize) {
        super(manager, model, shadowSize);
    }

    @Override
    public ResourceLocation getTextureLocation(@NotNull Broomstick broomstick) {
        return null;
    }
    @Override
    public RenderType getRenderType(@NotNull Broomstick entity, boolean showBody, boolean translucent, boolean showOutline) {
        return RENDER_TYPE;
    }
}
