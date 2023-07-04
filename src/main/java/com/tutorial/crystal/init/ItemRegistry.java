package com.tutorial.crystal.init;

import com.tutorial.crystal.Armor.CrystalArmor;
import com.tutorial.crystal.Utils;
import com.tutorial.crystal.item.*;
import com.tutorial.crystal.sword.ReachItem;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.tutorial.crystal.init.BlockRegistry.REIMU_BLOCK;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);
    public static final RegistryObject<Item> awaqwq = ITEMS.register("awaqwq", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CrystalSword = ITEMS.register("crystal_sword" , () -> new ReachItem(ItemTierRegistry.CRYSTAL ,new Item.Properties().stacksTo(8)));
    public static final RegistryObject<Item> JudgeSword1 = ITEMS.register("judge_sword1" , () -> new SwordItem(ItemTierRegistry.CRYSTAL,4,-2.4F,new Item.Properties()));
    public static final RegistryObject<Item> JudgeSword2 = ITEMS.register("judge_sword2" , () -> new SwordItem(ItemTierRegistry.CRYSTAL,4,-2.4F,new Item.Properties()));
    public static final RegistryObject<Item> AspectOfEnd = ITEMS.register("aspect_of_end",() -> new SwordItem(ItemTierRegistry.CRYSTAL,4,-24,new Item.Properties()));
    public static final RegistryObject<Item> JetPack = ITEMS.register("jet_pack" , () -> new CrystalArmor(ArmorMaterials.CHAIN, ArmorItem.Type.CHESTPLATE ,new Item.Properties()));
    public static final RegistryObject<Item> AdvancedJetPack = ITEMS.register("advanced_jet_pack", () -> new CrystalArmor(ArmorMaterials.IRON, ArmorItem.Type.CHESTPLATE , new Item.Properties()));
    public static final RegistryObject<Item> Fishingdragger = ITEMS.register("fishing_dragger" , () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> Stone2Gold = ITEMS.register("stone_to_gold" , () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> HakureiGohei = ITEMS.register("hakurei_gohei", ItemHakureiGohei::new);
    public static final RegistryObject<Item> SanaeGohei = ITEMS.register("sanae_gohei", ItemSanaeGohei::new);
    public static final RegistryObject<Item> ItemMiniHakkero = ITEMS.register("mini_hakkero", ItemMiniHakkero::new);
    public static final RegistryObject<Item> ItemDanmaku = ITEMS.register("danmaku", () -> new DanmakuItem(new Item.Properties()));
    public static final RegistryObject<Item> ItemStarDanmaku = ITEMS.register("star_danmaku", () -> new StarDanmakuItem(new Item.Properties()));
    public static final RegistryObject<Item> ReimuItem = ITEMS.register("reimu_item", () -> new BlockItem(REIMU_BLOCK.get(),new Item.Properties()));
    public static final RegistryObject<Item> Boqi = ITEMS.register("bo_qi", () -> new ForgeSpawnEggItem(EntityTypeRegistry.CRYSTAL_1921,0xFFFFFF, 0xFFFFFF,new Item.Properties()));
    public static final RegistryObject<Item> HakureiReimu = ITEMS.register("hakurei_reimu", () -> new ForgeSpawnEggItem(EntityTypeRegistry.REIMU, 0xFFFFFF, 0xFFFFFF,new Item.Properties()));
}
