package com.tutorial.lively_danmaku.Entity.render;

import com.tutorial.lively_danmaku.Entity.Reimu;
import com.tutorial.lively_danmaku.Entity.layer.ReimuModelLayer;
import com.tutorial.lively_danmaku.Entity.model.ReimuModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ReimuRender extends MobRenderer<Reimu, ReimuModel<Reimu>> {
    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("lively_danmaku", "textures/entity/reimu.png");
    private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(TEXTURE_LOCATION);
    public ReimuRender(EntityRendererProvider.Context manager, ReimuModel<Reimu> model, float shadowSize) {
        super(manager, model, shadowSize);
        addLayer(new ReimuModelLayer(this,manager.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(Reimu p_114482_) {
        return null;
    }
    @Override
    public RenderType getRenderType(Reimu entity, boolean showBody, boolean translucent, boolean showOutline) {
        return RENDER_TYPE;
    }
}
