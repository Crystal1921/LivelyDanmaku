package com.tutorial.crystal.Enchantment;

import com.tutorial.crystal.Utils;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EnchantmentRegistry {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Utils.MOD_ID);
    public static final RegistryObject<Enchantment> fall_resistance = ENCHANTMENTS.register("fall_resistance", () -> new FallResistance(Enchantment.Rarity.UNCOMMON));
}
