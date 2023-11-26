package com.tutorial.lively_danmaku.init;

import com.tutorial.lively_danmaku.gui.*;
import com.tutorial.lively_danmaku.Utils;
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
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Utils.MOD_ID);
    public static final RegistryObject<MenuType<DanmakuMenu>> DANMAKU_CRAFT = registerMenuType(DanmakuMenu::new, "danmaku_craft");
    public static final RegistryObject<MenuType<AdvancedDanmakuMenu>> ADVANCED_DANMAKU_TABLE = registerMenuType(AdvancedDanmakuMenu::new,"advanced_danmaku_craft" );
    public static final RegistryObject<MenuType<FumoTableMenu>> FUMO_TABLE = registerMenuType(FumoTableMenu::new, "fumo_table");
    public static final RegistryObject<MenuType<EmitterMenu>> EMITTER_MENU = registerMenuType(EmitterMenu::new, "danmaku_emitter");
    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name){
        return CONTAINERS.register(name,()-> IForgeMenuType.create(factory));
    }
    @OnlyIn(Dist.CLIENT)
    public static void renderScreens() {
        MenuScreens.register(DANMAKU_CRAFT.get(), DanmakuScreen::new);
        MenuScreens.register(FUMO_TABLE.get(), FumoTableScreen::new);
        MenuScreens.register(ADVANCED_DANMAKU_TABLE.get(), AdvancedDanmakuScreen::new);
        MenuScreens.register(EMITTER_MENU.get(), EmitterScreen::new);
    }
}
