package com.tutorial.crystal.Entity.render;

import com.tutorial.crystal.Entity.player;
import com.tutorial.crystal.Entity.model.PlayerModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class PlayerRender extends MobRenderer<player, PlayerModel<player>> {
    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("crystal", "textures/player/boqi_1.png");
    private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(TEXTURE_LOCATION);
    public PlayerRender(EntityRendererProvider.Context manager, PlayerModel<player> model, float shadowSize) {
        super(manager, model, shadowSize);
    }
    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull player p_114482_) {
        return TEXTURE_LOCATION;
    }
    @Override
    public RenderType getRenderType(@NotNull player entity, boolean showBody, boolean translucent, boolean showOutline) {
        return RENDER_TYPE;
    }
}
