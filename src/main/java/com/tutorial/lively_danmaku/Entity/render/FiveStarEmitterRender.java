package com.tutorial.lively_danmaku.Entity.render;

import com.tutorial.lively_danmaku.Entity.FiveStarEmitter;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class FiveStarEmitterRender extends EntityRenderer<FiveStarEmitter> {
    public FiveStarEmitterRender(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public ResourceLocation getTextureLocation(FiveStarEmitter p_114482_) {
        return null;
    }
}
