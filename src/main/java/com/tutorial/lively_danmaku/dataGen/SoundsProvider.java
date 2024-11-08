package com.tutorial.lively_danmaku.dataGen;

import com.tutorial.lively_danmaku.LivelyDanmaku;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;
import net.minecraftforge.registries.RegistryObject;

public class SoundsProvider extends SoundDefinitionsProvider {
    public SoundsProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, LivelyDanmaku.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {

    }


    public void makeMusicDisc(RegistryObject<SoundEvent> event, String discName) {
        this.add(event, SoundDefinition.definition()
                .with(SoundDefinition.Sound.sound(new ResourceLocation(LivelyDanmaku.MOD_ID, discName), SoundDefinition.SoundType.SOUND)
                        .stream()));
    }
}
