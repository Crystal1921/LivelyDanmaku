package com.tutorial.lively_danmaku.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import com.tutorial.lively_danmaku.Utils;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import static com.tutorial.lively_danmaku.LivelyDanmaku.prefix;

public class SoundRegistry {
    public static final List<SoundEvent> EVENTS = new ArrayList<>();
    public static final SoundEvent th08_10_love_color = makeSoundEvent("th08_10_love_color");

    private static SoundEvent makeSoundEvent(String name) {
        SoundEvent event = SoundEvent.createVariableRangeEvent(prefix(name));
        EVENTS.add(event);
        return event;
    }

    public static void init(BiConsumer<SoundEvent, ResourceLocation> r) {
        for (SoundEvent event : EVENTS) {
            r.accept(event, event.getLocation());
        }
    }
}
