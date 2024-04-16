package com.tutorial.lively_danmaku;

import com.tutorial.lively_danmaku.config.DanmakuConfig;
import com.tutorial.lively_danmaku.init.EnchantmentRegistry;
import com.tutorial.lively_danmaku.init.GroupInit;
import com.tutorial.lively_danmaku.init.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;

import java.util.Locale;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Mod(LivelyDanmaku.MOD_ID)
public class LivelyDanmaku {
    public static final String MOD_ID = "lively_danmaku";

    public LivelyDanmaku() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ItemRegistry.ITEMS.register(eventBus);
        EntityTypeRegistry.ENTITY_TYPE.register(eventBus);
        EnchantmentRegistry.ENCHANTMENTS.register(eventBus);
        BlockRegistry.BLOCKS.register(eventBus);
        BlockEntityTypeRegistry.BLOCK_ENTITY_TYPES.register(eventBus);
        MenuRegistry.CONTAINERS.register(eventBus);
        GroupInit.TABS.register(eventBus);
        eventBus.addListener(EntityTypeRegistry::addEntityAttributes);
        bind(eventBus,Registries.SOUND_EVENT,SoundRegistry::init);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DanmakuConfig.init());
        //TODO 符卡制作台
        //TODO 弹幕函数台
        //TODO 配套原作音效
        //TODO 完善教程--帕秋莉手册（英文未完成）
    }

    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(MOD_ID, name.toLowerCase(Locale.ROOT));
    }

    private static <T> void bind(IEventBus eventBus, ResourceKey<Registry<T>> registry, Consumer<BiConsumer<T, ResourceLocation>> source) {
        eventBus.addListener((RegisterEvent event) -> source.accept((t, rl) -> event.register(registry, rl, () -> t)));
    }
}