package com.tutorial.lively_danmaku.init;

import com.tutorial.lively_danmaku.LivelyDanmaku;
import com.tutorial.lively_danmaku.entity.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
public class EntityTypeRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, LivelyDanmaku.MOD_ID);
    public static final RegistryObject<EntityType<NormalDanmaku>> DANMAKU = ENTITY_TYPE.register("danmaku", () ->
            EntityType.Builder.of((EntityType<NormalDanmaku> type, Level level) -> new NormalDanmaku(type, level), MobCategory.MISC)
                    .sized(0.6F, 0.6F)
                    .build("danmaku")
    );
    public static final RegistryObject<EntityType<StarDanmaku>> STAR_DANMAKU = ENTITY_TYPE.register("star_danmaku", () ->
            EntityType.Builder.of(StarDanmaku::new, MobCategory.MISC)
                    .sized(0.6F,0.6F)
                    .build("star_danmaku"));
    public static final RegistryObject<EntityType<HakureiDanmaku>> HAKUREI_BULLET = ENTITY_TYPE.register("hakurei_bullet", () ->
            EntityType.Builder.of((EntityType<HakureiDanmaku> type, Level level) -> new HakureiDanmaku(type, level), MobCategory.MISC)
                    .sized(0.5F,0.5F)
                    .build("hakurei_bullet"));
    public static final RegistryObject<EntityType<FiveStarEmitter>> FIVE_STAR_EMITTER = ENTITY_TYPE.register("five_star_emitter", () ->
            EntityType.Builder.of((EntityType<FiveStarEmitter> type, Level level) -> new FiveStarEmitter(type, level), MobCategory.MISC)
                    .sized(0F,0F)
                    .build("five_star_emitter"));
    public static final RegistryObject<EntityType<YinYangOrb>> YIN_YANG_ORB = ENTITY_TYPE.register("yin_yang_orb", () ->
            EntityType.Builder.of(YinYangOrb::new, MobCategory.MISC)
                    .sized(0.6F,0.6F)
                    .build("yin_yang_orb"));
    public static final RegistryObject<EntityType<Reimu>> REIMU = ENTITY_TYPE.register("reimu", () ->
            EntityType.Builder.of(Reimu::new, MobCategory.CREATURE)
                    .sized(0.6F,1.6F)
                    .build("reimu")
    );
    public static final RegistryObject<EntityType<FakePlayer>> FAKE_PLAYER = ENTITY_TYPE.register("fake_player", () ->
            EntityType.Builder.of(FakePlayer::new, MobCategory.CREATURE)
                    .build("fake_player")
    );
    public static final RegistryObject<EntityType<Broomstick>> BROOMSTICK = ENTITY_TYPE.register("broomstick", () ->
            EntityType.Builder.of(Broomstick::new, MobCategory.MISC)
                    .sized(0.6F,0.6F)
                    .build("broomstick"));
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(REIMU.get(), Reimu.registerAttributes().build());
        event.put(FAKE_PLAYER.get(), FakePlayer.registerAttributes().build());
        event.put(BROOMSTICK.get(), Broomstick.registerAttributes().build());
    }
}
