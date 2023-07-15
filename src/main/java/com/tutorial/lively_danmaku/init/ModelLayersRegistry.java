package com.tutorial.lively_danmaku.init;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModelLayersRegistry {
    public static final ModelLayerLocation REIMU;
    public static final ModelLayerLocation FAKE_PLAYER;
    public static final ModelLayerLocation HEADDRESS;

    static {
        REIMU = register("reimu");
        FAKE_PLAYER = register("fake_player");
        HEADDRESS = register("headdress");
    }

    private static ModelLayerLocation register(String name) {
        return new ModelLayerLocation(new ResourceLocation("lively_danmaku",name),name);
    }
}
