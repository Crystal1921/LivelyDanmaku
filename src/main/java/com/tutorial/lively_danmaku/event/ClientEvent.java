package com.tutorial.lively_danmaku.event;

import com.tutorial.lively_danmaku.Utils;
import com.tutorial.lively_danmaku.init.ItemRegistry;
import com.tutorial.lively_danmaku.item.DanmakuItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Utils.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvent {
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onColorRegister(RegisterColorHandlersEvent.Item evt)
    {
        evt.register(
                (pStack, pTintIndex) ->
                        pTintIndex == 0 ? -1 : ((DanmakuItem)pStack.getItem()).getColor(pStack),
                ItemRegistry.ItemDanmaku.get()
        );
    }
}
