package com.tutorial.crystal.init;

import com.tutorial.crystal.Utils;
import com.tutorial.crystal.item.*;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.tutorial.crystal.init.BlockRegistry.DANMAKU_TABLE;
import static com.tutorial.crystal.init.BlockRegistry.REIMU_BLOCK;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);
    public static final RegistryObject<Item> HakureiGohei = ITEMS.register("hakurei_gohei", ItemHakureiGohei::new);
    public static final RegistryObject<Item> SanaeGohei = ITEMS.register("sanae_gohei", ItemSanaeGohei::new);
    public static final RegistryObject<Item> ItemMiniHakkero = ITEMS.register("mini_hakkero", ItemMiniHakkero::new);
    public static final RegistryObject<Item> ItemDanmaku = ITEMS.register("danmaku", () -> new DanmakuItem(new Item.Properties()));
    public static final RegistryObject<Item> ItemStarDanmaku = ITEMS.register("star_danmaku", () -> new StarDanmakuItem(new Item.Properties()));
    public static final RegistryObject<Item> P_Point = ITEMS.register("p_point", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> red_Point = ITEMS.register("red_point", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ReimuItem = ITEMS.register("reimu_item", () -> new BlockItem(REIMU_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> DanmakuTableItem = ITEMS.register("danmaku_table_item", () -> new BlockItem(DANMAKU_TABLE.get(), new Item.Properties()));
    public static final RegistryObject<Item> Player = ITEMS.register("player", () -> new ForgeSpawnEggItem(EntityTypeRegistry.CRYSTAL_1921,0xFFFFFF, 0xFFFFFF,new Item.Properties()));
    public static final RegistryObject<Item> HakureiReimu = ITEMS.register("hakurei_reimu", () -> new ForgeSpawnEggItem(EntityTypeRegistry.REIMU, 0xFFFFFF, 0xFFFFFF,new Item.Properties()));
}
