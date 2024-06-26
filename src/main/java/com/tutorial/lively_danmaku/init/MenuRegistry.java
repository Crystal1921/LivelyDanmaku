package com.tutorial.lively_danmaku.init;

import com.tutorial.lively_danmaku.LivelyDanmaku;
import com.tutorial.lively_danmaku.gui.menu.*;
import com.tutorial.lively_danmaku.gui.screen.*;
import net.minecraft.client.gui.screens.MenuScreens;
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
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, LivelyDanmaku.MOD_ID);
    public static final RegistryObject<MenuType<DanmakuMenu>> DANMAKU_CRAFT = registerMenuType(DanmakuMenu::new, "danmaku_craft");
    public static final RegistryObject<MenuType<AdvancedDanmakuMenu>> ADVANCED_DANMAKU_TABLE = registerMenuType(AdvancedDanmakuMenu::new,"advanced_danmaku_craft" );
    public static final RegistryObject<MenuType<EmitterMenu>> EMITTER_MENU = registerMenuType(EmitterMenu::new, "danmaku_emitter");
    public static final RegistryObject<MenuType<DanmakuImportMenu>> IMPORT_MENU = registerMenuType(DanmakuImportMenu::new, "danmaku_import");
    public static final RegistryObject<MenuType<DanmakuColorMenu>> COLOR_MENU = registerMenuType(DanmakuColorMenu::new,"danmaku_color");
    public static final RegistryObject<MenuType<DanmakuTraceMenu>> TRACE_MENU = registerMenuType(DanmakuTraceMenu::new, "danmaku_trace");
    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name){
        return CONTAINERS.register(name,()-> IForgeMenuType.create(factory));
    }
    @OnlyIn(Dist.CLIENT)
    public static void renderScreens() {
        MenuScreens.register(DANMAKU_CRAFT.get(), DanmakuScreen::new);
        MenuScreens.register(ADVANCED_DANMAKU_TABLE.get(), AdvancedDanmakuScreen::new);
        MenuScreens.register(EMITTER_MENU.get(), EmitterScreen::new);
        MenuScreens.register(IMPORT_MENU.get(), DanmakuImportScreen::new);
        MenuScreens.register(COLOR_MENU.get(), DanmakuColorScreen::new);
        MenuScreens.register(TRACE_MENU.get(), DanmakuTraceScreen::new);
    }
}
