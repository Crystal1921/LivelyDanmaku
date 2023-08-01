package com.tutorial.lively_danmaku.Entity.render;

import com.tutorial.lively_danmaku.Entity.Reimu;
import com.tutorial.lively_danmaku.Entity.layer.ReimuModelLayer;
import com.tutorial.lively_danmaku.Entity.model.ReimuModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ReimuRender extends MobRenderer<Reimu, ReimuModel<Reimu>> {
    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("lively_danmaku", "textures/entity/reimu.png");
    private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(TEXTURE_LOCATION);
    public ReimuRender(EntityRendererProvider.Context manager, ReimuModel<Reimu> model, float shadowSize) {
        super(manager, model, shadowSize);
        addLayer(new ReimuModelLayer(this,manager.getItemInHandRenderer()));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Reimu reimu) {
        return TEXTURE_LOCATION;
    }
    @Override
    public RenderType getRenderType(@NotNull Reimu entity, boolean showBody, boolean translucent, boolean showOutline) {
        return RENDER_TYPE;
    }
}
