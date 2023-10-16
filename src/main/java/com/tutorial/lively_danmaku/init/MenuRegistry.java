package com.tutorial.lively_danmaku.init;

import com.tutorial.lively_danmaku.GUI.*;
import com.tutorial.lively_danmaku.Utils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuRegistry {
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Utils.MOD_ID);
    public static final RegistryObject<MenuType<DanmakuMenu>> DANMAKU_CRAFT = CONTAINERS.register("danmaku_craft",
            () -> new MenuType<>(DanmakuMenu::new, FeatureFlags.REGISTRY.allFlags()));
    public static final RegistryObject<MenuType<AdvancedDanmakuMenu>> ADVANCED_DANMAKU_TABLE = CONTAINERS.register("advanced_danmaku_craft",
            () -> new MenuType<>(AdvancedDanmakuMenu::new, FeatureFlags.REGISTRY.allFlags()));
    public static final RegistryObject<MenuType<FumoTableMenu>> FUMO_TABLE = registerMenuType(FumoTableMenu::new, "fumo_table");
    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory,
                                                                                                 String name){
        return CONTAINERS.register(name,()-> IForgeMenuType.create(factory));
    }
    @OnlyIn(Dist.CLIENT)
    public static void renderScreens() {
        MenuScreens.register(DANMAKU_CRAFT.get(), DanmakuScreen::new);
        MenuScreens.register(FUMO_TABLE.get(), FumoTableScreen::new);
        MenuScreens.register(ADVANCED_DANMAKU_TABLE.get(), AdvancedDanmakuScreen::new);
    }
}
