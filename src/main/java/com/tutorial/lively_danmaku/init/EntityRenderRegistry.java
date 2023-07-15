package com.tutorial.lively_danmaku.init;

import com.tutorial.lively_danmaku.Entity.model.PlayerModel;
import com.tutorial.lively_danmaku.Entity.model.ReimuModel;
import com.tutorial.lively_danmaku.Entity.render.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EntityRenderRegistry {
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityTypeRegistry.DANMAKU.get(), DanmakuRender::new);
        event.registerEntityRenderer(EntityTypeRegistry.STAR_DANMAKU.get(), StarDanmakuRender::new);
        event.registerEntityRenderer(EntityTypeRegistry.HAKUREI_BULLET.get(), HakureiBulletRender::new);
        event.registerEntityRenderer(EntityTypeRegistry.YIN_YANG_ORB.get(), YinYangOrbRender::new);
        event.registerEntityRenderer(EntityTypeRegistry.FIVE_STAR_EMITTER.get(), FiveStarEmitterRender::new);
        event.registerEntityRenderer(EntityTypeRegistry.REIMU.get(), m -> new ReimuRender(m, new ReimuModel<>(m.bakeLayer(ModelLayersRegistry.REIMU)), 0.3F));
        event.registerEntityRenderer(EntityTypeRegistry.FAKE_PLAYER.get(), m -> new PlayerRender(m, new PlayerModel<>(m.bakeLayer(ModelLayersRegistry.FAKE_PLAYER),true),0.3F));
    }
}