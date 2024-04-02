package com.tutorial.lively_danmaku.init;

import com.tutorial.lively_danmaku.LivelyDanmaku;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import vazkii.patchouli.common.item.ItemModBook;

public class GroupInit {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, LivelyDanmaku.MOD_ID);

    public static final RegistryObject<CreativeModeTab> BLOCKS = TABS.register("blocks", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .title(Component.translatable("group.lively_danmaku"))
            .icon(() -> new ItemStack(ItemRegistry.ReimuItem.get()))
            .displayItems((parameters, output) -> {
                if (ModList.get().isLoaded("patchouli")) {
                    output.accept(ItemModBook.forBook(new ResourceLocation(LivelyDanmaku.MOD_ID, "danmaku_guide")));
                }
                    output.accept(ItemRegistry.HakureiGohei.get());
                    output.accept(ItemRegistry.SanaeGohei.get());
                    output.accept(ItemRegistry.ItemMiniHakkero.get());
                    output.accept(ItemRegistry.ReimuHeaddress.get());
                    output.accept(ItemRegistry.ItemDanmaku.get());
                    output.accept(ItemRegistry.ItemStarDanmaku.get());
                    output.accept(ItemRegistry.BigPoint.get());
                    output.accept(ItemRegistry.SmallPoint.get());
                    output.accept(ItemRegistry.HakureiBulletDanmaku.get());
                    output.accept(ItemRegistry.ReimuItem.get());
                    output.accept(ItemRegistry.FumoTable.get());
                    output.accept(ItemRegistry.DanmakuTableItem.get());
                    output.accept(ItemRegistry.AdvancedDanmakuTableItem.get());
                    output.accept(ItemRegistry.DanmakuImport.get());
                    output.accept(ItemRegistry.DanmakuColor.get());
                    output.accept(ItemRegistry.DanmakuTrace.get());
                    output.accept(ItemRegistry.DanmakuFunction.get());
                    output.accept(ItemRegistry.TH08_10_love_color.get());
                    output.accept(ItemRegistry.Player.get());
                    output.accept(ItemRegistry.HakureiReimu.get());
                    output.accept(ItemRegistry.Broomstick.get());
                    output.accept(ItemRegistry.DanmakuEmitter.get());
                }).build());
    }