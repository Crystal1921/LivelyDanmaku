package com.tutorial.lively_danmaku.init;

import com.tutorial.lively_danmaku.item.tooltip.ClientDanmakuTooltip;
import com.tutorial.lively_danmaku.item.tooltip.RecordDanmakuTooltip;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TooltipRegistry {
    @SubscribeEvent
    public static void onRegisterClientTooltip(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(RecordDanmakuTooltip.class,ClientDanmakuTooltip::new);
    }
}
