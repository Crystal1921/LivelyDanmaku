package com.tutorial.lively_danmaku.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class DanmakuConfig {
    public static ForgeConfigSpec.IntValue DANMAKU_NUM;
    public static ForgeConfigSpec.IntValue EMITTER_FREQUENCY;
    public static ForgeConfigSpec.IntValue SAMPLING_DENSITY;
    public static ForgeConfigSpec init() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("lively_danmaku");

        builder.comment("The max number of danmaku can be shot by gohei once");
        DANMAKU_NUM = builder.defineInRange("DanmakuNum", 1024, 1, 4096);

        builder.comment("The max frequency of the danmaku emitter");
        EMITTER_FREQUENCY = builder.defineInRange("EmitterFrequency",5,1,1000);

        builder.comment("The max sampling density can danmaku import");
        SAMPLING_DENSITY = builder.defineInRange("SamplingDensity",50,1,100);

        builder.pop();
        return builder.build();
    }
}
