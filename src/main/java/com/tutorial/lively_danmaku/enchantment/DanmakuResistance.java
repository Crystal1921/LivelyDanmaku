package com.tutorial.lively_danmaku.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class DanmakuResistance extends Enchantment {
    public DanmakuResistance(Rarity rarity) {
        super(rarity, EnchantmentCategory.ARMOR_CHEST, new EquipmentSlot[] {EquipmentSlot.CHEST});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
