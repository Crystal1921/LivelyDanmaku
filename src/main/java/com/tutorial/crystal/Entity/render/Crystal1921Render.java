package com.tutorial.crystal.Entity.render;

import com.tutorial.crystal.Entity.siborary;
import com.tutorial.crystal.Entity.model.Crystal1921Model;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Crystal1921Render extends MobRenderer<siborary, Crystal1921Model<siborary>> {
    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("crystal", "textures/player/boqi_1.png");
    private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(TEXTURE_LOCATION);
    public Crystal1921Render(EntityRendererProvider.Context manager, Crystal1921Model<siborary> model, float shadowSize) {
        super(manager, model, shadowSize);
    }
    @Override
    public ResourceLocation getTextureLocation(siborary p_114482_) {
        return null;
    }
    @Override
    public RenderType getRenderType(siborary entity, boolean showBody, boolean translucent, boolean showOutline) {
        return RENDER_TYPE;
    }
}
