package com.tutorial.lively_danmaku.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tutorial.lively_danmaku.entity.player;
import com.tutorial.lively_danmaku.entity.model.PlayerModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import static com.tutorial.lively_danmaku.entity.player.*;

@OnlyIn(Dist.CLIENT)
public class PlayerRender extends MobRenderer<player, PlayerModel<player>> {
    //public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("lively_danmaku", "textures/player/boqi_1.png");
    public PlayerRender(EntityRendererProvider.Context manager, PlayerModel<player> model, float shadowSize) {
        super(manager, model, shadowSize);
    }
    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull player entity) {
        Minecraft mc = Minecraft.getInstance();
        ResourceLocation texture = DefaultPlayerSkin.getDefaultSkin();
        if (!entity.getEntityData().get(EASTER_EGG)) {
            if (mc.getCameraEntity() instanceof AbstractClientPlayer client) {
                texture = client.getSkinTextureLocation();
            }
        }   else texture = new ResourceLocation("lively_danmaku", "textures/player/boqi_1.png");
        return texture;
    }
    @Override
    public void scale(@NotNull player fake_player, PoseStack stack, float partialTickTime) {
        float scale = fake_player.getEntityData().get(IS_SMALL) ? 0.4F : 1.0F;
        stack.scale(scale, scale, scale);
    }
}
