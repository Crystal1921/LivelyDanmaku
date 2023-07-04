package com.tutorial.crystal.init;

import com.tutorial.crystal.Entity.model.Crystal1921Model;
import com.tutorial.crystal.Entity.model.ReimuModel;
import com.tutorial.crystal.Utils;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Utils.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LayerRegistry {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModelLayersRegistry.REIMU, ReimuModel::create);
        event.registerLayerDefinition(ModelLayersRegistry.CRYSTAL, () -> LayerDefinition.create(Crystal1921Model.createMesh(CubeDeformation.NONE,true),64,64));
    }
}