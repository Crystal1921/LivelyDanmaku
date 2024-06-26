package com.tutorial.lively_danmaku.init;

import com.tutorial.lively_danmaku.LivelyDanmaku;
import com.tutorial.lively_danmaku.enchantment.DanmakuResistance;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EnchantmentRegistry {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, LivelyDanmaku.MOD_ID);
    public static final RegistryObject<Enchantment> danmaku_resistance = ENCHANTMENTS.register("danmaku_resistance", () -> new DanmakuResistance(Enchantment.Rarity.RARE));
}
