package com.tutorial.lively_danmaku.init;

import com.tutorial.lively_danmaku.GUI.DanmakuMenu;
import com.tutorial.lively_danmaku.GUI.DanmakuScreen;
import com.tutorial.lively_danmaku.Utils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuRegistry {
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Utils.MOD_ID);
    public static final RegistryObject<MenuType<DanmakuMenu>> DANMAKU_CRAFT = CONTAINERS.register("danmaku_craft",
            () -> new MenuType<>(DanmakuMenu::new, FeatureFlags.REGISTRY.allFlags()));
    @OnlyIn(Dist.CLIENT)
    public static void renderScreens() {
        MenuScreens.register(DANMAKU_CRAFT.get(), DanmakuScreen::new);
    }
}
