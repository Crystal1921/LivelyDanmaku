package com.tutorial.crystal.init;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModelLayersRegistry {
    public static final ModelLayerLocation REIMU;
    public static final ModelLayerLocation CRYSTAL;

    static {
        REIMU = register("reimu");
        CRYSTAL = register("crystal1921");
    }

    private static ModelLayerLocation register(String name) {
        return new ModelLayerLocation(new ResourceLocation("crystal",name),name);
    }
}
