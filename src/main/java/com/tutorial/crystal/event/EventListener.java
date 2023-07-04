package com.tutorial.crystal.event;

import com.tutorial.crystal.Enchantment.EnchantmentRegistry;
import com.tutorial.crystal.init.BlockRegistry;
import com.tutorial.crystal.init.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.InputEvent.Key;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber
public class EventListener {
    @SubscribeEvent
    public static void entityHurts(LivingHurtEvent event) {
        LivingEntity living = event.getEntity();
        DamageSource source = event.getSource();
        Level level = event.getEntity().level();
        Entity trueSource = source.getEntity();
        if (trueSource instanceof Player) {
            ItemStack itemStack = ((Player) trueSource).getMainHandItem();
            if (itemStack.is(ItemRegistry.JudgeSword1.get()) && itemStack.getDamageValue() == 5) {
                living.teleportTo(living.getX(), living.getY() + 2, living.getZ());
                living.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 20, 0));
                ((ServerLevel) level).sendParticles(ParticleTypes.CLOUD, living.getX(), living.getY() + living.getBbHeight() * 0.5F, living.getZ(), 128, living.getBbWidth() * 0.5, living.getBbHeight() * 0.5, living.getBbWidth() * 0.5, 0);
                level.explode(null, living.getX(), living.getY() + 2, living.getZ(), 1.0F, Level.ExplosionInteraction.MOB);
                if (!source.isCreativePlayer()) {
                    ((Player) trueSource).getMainHandItem().setDamageValue(0);
                }
            }
            if (itemStack.is(ItemRegistry.JudgeSword2.get()) && itemStack.getDamageValue() == 10) {
                float rotation = trueSource.getYRot();
                double d1 = 3 * -Mth.sin(rotation * ((float) Math.PI / 180F));
                double d2 = 3 * Mth.cos(rotation * ((float) Math.PI / 180F));
                level.explode(null, trueSource.getX() + d1, trueSource.getY(), trueSource.getZ() + d2, 2.0F, Level.ExplosionInteraction.MOB);
                if (!source.isCreativePlayer()) {
                    ((Player) trueSource).getMainHandItem().setDamageValue(0);
                }
            }
        }
    }

    @SubscribeEvent
    public static void FallHurt(LivingFallEvent event) {
        LivingEntity player = event.getEntity();
        if (player instanceof Player) {
            int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.fall_resistance.get(), player);
            if (level > 0) {
                event.setDamageMultiplier(0);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onKeyInput(@NotNull Key event) {
        if (event.getKey() == GLFW.GLFW_KEY_SPACE) {
            Player player = Minecraft.getInstance().player;
            ItemStack itemStack = player.getItemBySlot(EquipmentSlot.CHEST);
            if(itemStack.is(ItemRegistry.JetPack.get())) {
                Vec3 motion = player.getDeltaMovement();
                if (player.isFallFlying()) {
                    Vec3 forward = player.getLookAngle();
                    Vec3 delta = forward.multiply(forward.scale(0.15))
                            .add(forward.scale(1.5).subtract(motion).scale(0.5))
                            .multiply(2,2,2);
                    player.setDeltaMovement(motion.add(delta));
                } else {
                    player.setDeltaMovement(motion.x(), Math.min(motion.y() + 0.15D, 0.5D), motion.z());
                }
            }
        }
    }

    @SubscribeEvent
    public static void RightClick(PlayerInteractEvent event){
        Player player = event.getEntity();
        ItemStack itemStack = event.getItemStack();
        if(event instanceof PlayerInteractEvent.RightClickItem){
            BlockState blockState = player.getBlockStateOn();
            if(itemStack.is(ItemRegistry.AspectOfEnd.get())) {
                if (blockState.is(Blocks.AIR)){
                    Vec3 target_pos = player.getPosition(0).add(player.getLookAngle().scale(5));
                    player.setPos(target_pos);
                    player.setDeltaMovement(Vec3.ZERO);
                }
            }
            if(itemStack.is(ItemRegistry.Fishingdragger.get())){
                if (blockState.is(Blocks.AIR)){
                    Vec3 Voc = player.getDeltaMovement().add(player.getLookAngle().scale(4));
                    player.setDeltaMovement(Voc);
                }
            }
        }
        if (event instanceof PlayerInteractEvent.RightClickBlock){
            BlockPos pos = event.getPos();
            Level level = event.getLevel();
            BlockState state = level.getBlockState(pos);
            if (state.is(Blocks.STONE) && itemStack.is(ItemRegistry.Stone2Gold.get())){
                level.setBlockAndUpdate(pos,Blocks.GOLD_BLOCK.defaultBlockState());
                if (!player.isCreative()) {
                    itemStack.setCount(itemStack.getCount() - 1);
                }
            }
            if (state.is(BlockRegistry.REIMU_BLOCK.get())) {
                player.sendSystemMessage(Component.translatable("chat.crystal.reimu"));
            }
        }
    }

    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        Player eventPlayer = event.player;
        ItemStack itemStack = eventPlayer.getItemBySlot(EquipmentSlot.CHEST);
        if (!eventPlayer.isCreative()) {
            if (itemStack.is(ItemRegistry.AdvancedJetPack.get())) {
                eventPlayer.getAbilities().mayfly = true;
            }   else {
                eventPlayer.getAbilities().mayfly = false;
                eventPlayer.getAbilities().flying = false;
            }
        }
    }
}