package com.tutorial.crystal;

import com.tutorial.crystal.Enchantment.EnchantmentRegistry;
import com.tutorial.crystal.Group.Group;
import com.tutorial.crystal.init.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Locale;


@Mod(Utils.MOD_ID)
public class crystal {
    public crystal() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ItemRegistry.ITEMS.register(eventBus);
        EntityTypeRegistry.ENTITY_TYPE.register(eventBus);
        EnchantmentRegistry.ENCHANTMENTS.register(eventBus);
        eventBus.addListener(EntityTypeRegistry::addEntityAttributes);
        BlockRegistry.BLOCKS.register(eventBus);
        BlockEntityTypeRegistry.BLOCK_ENTITY_TYPES.register(eventBus);
        Group.TABS.register(eventBus);
    }
    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(Utils.MOD_ID, name.toLowerCase(Locale.ROOT));
    }
}

