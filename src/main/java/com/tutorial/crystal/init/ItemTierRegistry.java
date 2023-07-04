package com.tutorial.crystal.init;

import com.tutorial.crystal.crystal;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class ItemTierRegistry {
    public static final Tier CRYSTAL = TierSortingRegistry.registerTier(
            new ForgeTier(2, 12, 6.5F, 2, 25, BlockTags.create(crystal.prefix("needs_awa_qwq")), () -> Ingredient.of(ItemRegistry.awaqwq.get())),
            crystal.prefix("ironwood"), List.of(Tiers.IRON), List.of(Tiers.DIAMOND));
}
