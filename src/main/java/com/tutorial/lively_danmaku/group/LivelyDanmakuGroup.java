package com.tutorial.lively_danmaku.group;

import com.tutorial.lively_danmaku.Utils;
import com.tutorial.lively_danmaku.init.ItemRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import vazkii.patchouli.common.item.ItemModBook;

public class LivelyDanmakuGroup {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Utils.MOD_ID);

    public static final RegistryObject<CreativeModeTab> BLOCKS = TABS.register("blocks", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .title(Component.translatable("group.lively_danmaku"))
            .icon(() -> new ItemStack(ItemRegistry.ReimuItem.get()))
            .displayItems((parameters, output) -> {
                if (ModList.get().isLoaded("patchouli")) {
                    output.accept(ItemModBook.forBook(new ResourceLocation(Utils.MOD_ID, "danmaku_guide")));
                }
                    output.accept(ItemRegistry.HakureiGohei.get());
                    output.accept(ItemRegistry.SanaeGohei.get());
                    output.accept(ItemRegistry.ItemMiniHakkero.get());
                    output.accept(ItemRegistry.ReimuHeaddress.get());
                    output.accept(ItemRegistry.ItemDanmaku.get());
                    output.accept(ItemRegistry.ItemStarDanmaku.get());
                    output.accept(ItemRegistry.P_Point.get());
                    output.accept(ItemRegistry.HakureiBulletDanmaku.get());
                    output.accept(ItemRegistry.red_Point.get());
                    output.accept(ItemRegistry.ReimuItem.get());
                    output.accept(ItemRegistry.FumoTable.get());
                    output.accept(ItemRegistry.DanmakuTableItem.get());
                    output.accept(ItemRegistry.AdvancedDanmakuTableItem.get());
                    output.accept(ItemRegistry.DanmakuEmitter.get());
                    output.accept(ItemRegistry.DanmakuImport.get());
                    output.accept(ItemRegistry.DanmakuColor.get());
                    output.accept(ItemRegistry.TH08_10_love_color.get());
                    output.accept(ItemRegistry.Player.get());
                    output.accept(ItemRegistry.HakureiReimu.get());
                    output.accept(ItemRegistry.Broomstick.get());
                }).build());
    }