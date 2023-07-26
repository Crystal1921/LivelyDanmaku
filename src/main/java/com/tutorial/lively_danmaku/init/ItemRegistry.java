package com.tutorial.lively_danmaku.init;

import com.tutorial.lively_danmaku.Armor.ReimuHeaddress;
import com.tutorial.lively_danmaku.Utils;
import com.tutorial.lively_danmaku.item.*;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.tutorial.lively_danmaku.init.BlockRegistry.DANMAKU_TABLE;
import static com.tutorial.lively_danmaku.init.BlockRegistry.REIMU_BLOCK;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);
    public static final RegistryObject<Item> HakureiGohei = ITEMS.register("hakurei_gohei", ItemHakureiGohei::new);
    public static final RegistryObject<Item> SanaeGohei = ITEMS.register("sanae_gohei", ItemSanaeGohei::new);
    public static final RegistryObject<Item> ItemMiniHakkero = ITEMS.register("mini_hakkero", ItemMiniHakkero::new);
    public static final RegistryObject<Item> ReimuHeaddress = ITEMS.register("reimu_headdress", () -> new ReimuHeaddress(ArmorMaterials.IRON, ArmorItem.Type.HELMET , new Item.Properties()));
    public static final RegistryObject<Item> ItemDanmaku = ITEMS.register("danmaku", () -> new DanmakuItem(new Item.Properties()));
    public static final RegistryObject<Item> ItemStarDanmaku = ITEMS.register("star_danmaku", () -> new StarDanmakuItem(new Item.Properties()));
    public static final RegistryObject<Item> P_Point = ITEMS.register("p_point", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> red_Point = ITEMS.register("red_point", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ReimuItem = ITEMS.register("reimu_item", () -> new BlockItem(REIMU_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> DanmakuTableItem = ITEMS.register("danmaku_table_item", () -> new BlockItem(DANMAKU_TABLE.get(), new Item.Properties()));
    public static final RegistryObject<Item> Player = ITEMS.register("fake_player", () -> new ForgeSpawnEggItem(EntityTypeRegistry.FAKE_PLAYER,0xFFFFFF, 0xFFFFFF,new Item.Properties()));
    public static final RegistryObject<Item> HakureiReimu = ITEMS.register("hakurei_reimu", () -> new ForgeSpawnEggItem(EntityTypeRegistry.REIMU, 0xFFFFFF, 0xFFFFFF,new Item.Properties()));
}