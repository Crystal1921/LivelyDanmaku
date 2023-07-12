package com.tutorial.crystal.Group;

import com.tutorial.crystal.Utils;
import com.tutorial.crystal.init.ItemRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class Group {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Utils.MOD_ID);

    public static final RegistryObject<CreativeModeTab> BLOCKS = TABS.register("blocks", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .title(Component.translatable("itemGroup.crystal"))
            .icon(() -> new ItemStack(ItemRegistry.ReimuItem.get()))
            .displayItems((parameters, output) -> {
                    output.accept(ItemRegistry.HakureiGohei.get());
                    output.accept(ItemRegistry.SanaeGohei.get());
                    output.accept(ItemRegistry.ItemMiniHakkero.get());
                    output.accept(ItemRegistry.ItemDanmaku.get());
                    output.accept(ItemRegistry.ItemStarDanmaku.get());
                    output.accept(ItemRegistry.P_Point.get());
                    output.accept(ItemRegistry.red_Point.get());
                    output.accept(ItemRegistry.ReimuItem.get());
                    output.accept(ItemRegistry.DanmakuTableItem.get());
                    output.accept(ItemRegistry.Player.get());
                    output.accept(ItemRegistry.HakureiReimu.get());
                }).build());
    }