package com.tutorial.lively_danmaku.init;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModelLayersRegistry {
    public static final ModelLayerLocation REIMU;
    public static final ModelLayerLocation lively_danmaku;

    static {
        REIMU = register("reimu");
        lively_danmaku = register("lively_danmaku1921");
    }

    private static ModelLayerLocation register(String name) {
        return new ModelLayerLocation(new ResourceLocation("lively_danmaku",name),name);
    }
}
