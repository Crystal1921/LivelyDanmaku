package com.tutorial.lively_danmaku;

import com.tutorial.lively_danmaku.init.EnchantmentRegistry;
import com.tutorial.lively_danmaku.group.LivelyDanmakuGroup;
import com.tutorial.lively_danmaku.init.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;

import java.util.Locale;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Mod(Utils.MOD_ID)
public class LivelyDanmaku {
    public LivelyDanmaku() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ItemRegistry.ITEMS.register(eventBus);
        EntityTypeRegistry.ENTITY_TYPE.register(eventBus);
        EnchantmentRegistry.ENCHANTMENTS.register(eventBus);
        BlockRegistry.BLOCKS.register(eventBus);
        BlockEntityTypeRegistry.BLOCK_ENTITY_TYPES.register(eventBus);
        MenuRegistry.CONTAINERS.register(eventBus);
        LivelyDanmakuGroup.TABS.register(eventBus);
        eventBus.addListener(EntityTypeRegistry::addEntityAttributes);
        bind(eventBus,Registries.SOUND_EVENT,SoundRegistry::init);
        //TODO 完善合成配方
        //TODO 配套原作音效
        //TODO 完善教程--帕秋莉手册
    }
    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(Utils.MOD_ID, name.toLowerCase(Locale.ROOT));
    }
    private static <T> void bind(IEventBus eventBus, ResourceKey<Registry<T>> registry, Consumer<BiConsumer<T, ResourceLocation>> source) {
        eventBus.addListener((RegisterEvent event) -> source.accept((t, rl) -> event.register(registry, rl, () -> t)));
    }
}

