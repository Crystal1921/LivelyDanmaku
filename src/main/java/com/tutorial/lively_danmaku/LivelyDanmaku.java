package com.tutorial.lively_danmaku;

import com.tutorial.lively_danmaku.init.EnchantmentRegistry;
import com.tutorial.lively_danmaku.group.LivelyDanmakuGroup;
import com.tutorial.lively_danmaku.init.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Locale;

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
    }
    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(Utils.MOD_ID, name.toLowerCase(Locale.ROOT));
    }
}

