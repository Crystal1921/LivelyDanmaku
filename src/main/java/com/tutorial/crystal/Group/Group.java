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
            .icon(() -> new ItemStack(ItemRegistry.Boqi.get()))
            .displayItems((parameters, output) -> {
                    output.accept(ItemRegistry.awaqwq.get());
                    output.accept(ItemRegistry.CrystalSword.get());
                    output.accept(ItemRegistry.JudgeSword1.get());
                    output.accept(ItemRegistry.JudgeSword2.get());
                    output.accept(ItemRegistry.JetPack.get());
                    output.accept(ItemRegistry.AdvancedJetPack.get());
                    output.accept(ItemRegistry.AspectOfEnd.get());
                    output.accept(ItemRegistry.Fishingdragger.get());
                    output.accept(ItemRegistry.Stone2Gold.get());
                    output.accept(ItemRegistry.HakureiGohei.get());
                    output.accept(ItemRegistry.SanaeGohei.get());
                    output.accept(ItemRegistry.ItemMiniHakkero.get());
                    output.accept(ItemRegistry.ItemDanmaku.get());
                    output.accept(ItemRegistry.ItemStarDanmaku.get());
                    output.accept(ItemRegistry.ReimuItem.get());
                    output.accept(ItemRegistry.Boqi.get());
                    output.accept(ItemRegistry.HakureiReimu.get());
                }).build());
    }