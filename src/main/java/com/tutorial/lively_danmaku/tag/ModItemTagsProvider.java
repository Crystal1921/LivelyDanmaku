package com.tutorial.lively_danmaku.tag;

import com.tutorial.lively_danmaku.Utils;
import com.tutorial.lively_danmaku.init.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> future, CompletableFuture<TagLookup<Block>> provider, ExistingFileHelper helper) {
        super(output, future, provider, Utils.MOD_ID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(ItemTags.MUSIC_DISCS).add(ItemRegistry.TH08_10_love_color.get());
    }
}
