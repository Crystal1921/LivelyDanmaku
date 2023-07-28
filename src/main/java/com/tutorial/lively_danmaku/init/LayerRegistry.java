package com.tutorial.lively_danmaku.init;

import com.tutorial.lively_danmaku.Entity.model.BroomstickModel;
import com.tutorial.lively_danmaku.Entity.model.HeaddressModel;
import com.tutorial.lively_danmaku.Entity.model.PlayerModel;
import com.tutorial.lively_danmaku.Entity.model.ReimuModel;
import com.tutorial.lively_danmaku.Utils;
import net.minecraft.client.model.geom.LayerDefinitions;
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
        event.registerLayerDefinition(ModelLayersRegistry.BROOMSTICK, BroomstickModel::create);
        event.registerLayerDefinition(ModelLayersRegistry.FAKE_PLAYER, () -> LayerDefinition.create(PlayerModel.createMesh(CubeDeformation.NONE,true),64,64));
        event.registerLayerDefinition(ModelLayersRegistry.HEADDRESS,() -> LayerDefinition.create(HeaddressModel.setup(LayerDefinitions.INNER_ARMOR_DEFORMATION),64,32));
    }
}