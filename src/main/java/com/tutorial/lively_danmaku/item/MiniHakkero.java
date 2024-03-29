package com.tutorial.lively_danmaku.item;

import com.tutorial.lively_danmaku.entity.StarDanmaku;
import com.tutorial.lively_danmaku.init.EntityTypeRegistry;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class MiniHakkero extends BowItem {
    Random random = new Random();
    public MiniHakkero() {
        super(new Properties()
                .durability(811));//新作魔理沙第一次登场于东方红魔乡中，发售于2002年8月11日
    }

    @Override
    public void releaseUsing(@NotNull ItemStack item, Level level, @NotNull LivingEntity living, int i) {
        if (!level.isClientSide) {
            if (living instanceof Player player) {
                int k = this.getUseDuration(item) - i;
                k = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(item, level, player, k, true);
                if (k <= 10) k = 10;
                float f = getPowerForTime(k);
                if (f > 0.8) {
                    for (int j = 0; j < k; j++) {
                        StarDanmaku star = new StarDanmaku(EntityTypeRegistry.STAR_DANMAKU.get(), level);
                        star.moveTo(living.getX(), living.getY() + 0.5, living.getZ());
                        float speed = f + random.nextFloat(1);
                        star.shootFromRotation(living, living.getXRot() + random.nextInt(41) - 20, living.getYRot() + random.nextInt(41) - 20, 0, speed, 1);
                        level.addFreshEntity(star);
                    }
                    item.hurtAndBreak(1, player, (player1) -> player1.broadcastBreakEvent(player.getUsedItemHand()));
                }
            }
        }
    }

    public void onUseTick(@NotNull Level pLevel, @NotNull LivingEntity pLivingEntity, @NotNull ItemStack pStack, int pRemainingUseDuration) {
        if (pLevel instanceof ClientLevel){
            for (int i = 0; i < 10; i++) {
                double distance = i + 1.5; // 距离玩家的距离

                float rotationX = pLivingEntity.getXRot();
                float rotationY = pLivingEntity.getYRot();

                double offsetX = -Math.sin(Math.toRadians(rotationY)) * Math.cos(Math.toRadians(rotationX)) * distance;
                double offsetY = -Math.sin(Math.toRadians(rotationX)) * distance + 1.75;
                double offsetZ = Math.cos(Math.toRadians(rotationY)) * Math.cos(Math.toRadians(rotationX)) * distance;

                pLevel.addParticle(ParticleTypes.END_ROD,pLivingEntity.getX() + offsetX,pLivingEntity.getY() + offsetY,pLivingEntity.getZ() + offsetZ,0,0,0);
            }
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(itemstack);
    }
}
