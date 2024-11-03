package com.tutorial.lively_danmaku.event;

import com.tutorial.lively_danmaku.entity.Broomstick;
import com.tutorial.lively_danmaku.entity.FakePlayer;
import com.tutorial.lively_danmaku.entity.Reimu;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.tutorial.lively_danmaku.init.EntityTypeRegistry.*;

@Mod.EventBusSubscriber
public class EntityAttributeEvent {
    @SubscribeEvent
    public void onEntityAttributeChange(EntityAttributeCreationEvent event){
        event.put(REIMU.get(), Reimu.registerAttributes().build());
        event.put(FAKE_PLAYER.get(), FakePlayer.registerAttributes().build());
        event.put(BROOMSTICK.get(), Broomstick.registerAttributes().build());
    }
}
