package com.tutorial.crystal.init;

import com.tutorial.crystal.Entity.model.Crystal1921Model;
import com.tutorial.crystal.Entity.model.ReimuModel;
import com.tutorial.crystal.Entity.render.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EntityRenderRegistry {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent evt) {
        BlockEntityTypeRegistry.registerTileEntityRenders();
    }
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityTypeRegistry.DANMAKU.get(), DanmakuRender::new);
        event.registerEntityRenderer(EntityTypeRegistry.STAR_DANMAKU.get(), StarDanmakuRender::new);
        event.registerEntityRenderer(EntityTypeRegistry.HAKUREI_BULLET.get(), HakureiBulletRender::new);
        event.registerEntityRenderer(EntityTypeRegistry.YIN_YANG_ORB.get(), YinYangOrbRender::new);
        event.registerEntityRenderer(EntityTypeRegistry.FIVE_STAR_EMITTER.get(), FiveStarEmitterRender::new);
        event.registerEntityRenderer(EntityTypeRegistry.REIMU.get(), m -> new ReimuRender(m, new ReimuModel<>(m.bakeLayer(ModelLayersRegistry.REIMU)), 0.3F));
        event.registerEntityRenderer(EntityTypeRegistry.CRYSTAL_1921.get(), m -> new Crystal1921Render(m, new Crystal1921Model<>(m.bakeLayer(ModelLayersRegistry.CRYSTAL),true),0.3F));
    }
}