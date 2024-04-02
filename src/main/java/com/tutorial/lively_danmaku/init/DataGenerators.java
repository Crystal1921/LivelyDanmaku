package com.tutorial.lively_danmaku.init;

import com.tutorial.lively_danmaku.LivelyDanmaku;
import com.tutorial.lively_danmaku.tag.ModBlockTagsProvider;
import com.tutorial.lively_danmaku.tag.ModItemTagsProvider;
import com.tutorial.lively_danmaku.tag.SoundsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = LivelyDanmaku.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        PackOutput output = event.getGenerator().getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();
        DataGenerator generator = event.getGenerator();
        CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();

        generator.addProvider(event.includeClient(),new SoundsProvider(output,helper));
        BlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(output,provider, LivelyDanmaku.MOD_ID,helper);

        generator.addProvider(event.includeServer(),blockTagsProvider);
        generator.addProvider(event.includeServer(),new ModItemTagsProvider(output,provider,blockTagsProvider.contentsGetter(),helper));
    }
}
