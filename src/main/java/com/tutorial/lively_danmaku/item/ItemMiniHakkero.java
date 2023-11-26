package com.tutorial.lively_danmaku.item;

import com.tutorial.lively_danmaku.entity.StarDanmaku;
import com.tutorial.lively_danmaku.init.EntityTypeRegistry;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class ItemMiniHakkero extends BowItem {
    Random random = new Random();
    public ItemMiniHakkero() {
        super(new Properties()
                .durability(500));
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
                        //double distance = j + 0.5; // 距离玩家的距离

                        //float rotationX = player.getXRot();
                        //float rotationY = player.getYRot();

                        //double offsetX = -Math.sin(Math.toRadians(rotationY)) * Math.cos(Math.toRadians(rotationX)) * distance;
                        //double offsetY = -Math.sin(Math.toRadians(rotationX)) * distance + 1.75;
                        //double offsetZ = Math.cos(Math.toRadians(rotationY)) * Math.cos(Math.toRadians(rotationX)) * distance;

                        //level.explode(player, player.getX() + offsetX, player.getY() + offsetY, player.getZ() + offsetZ, 1.0F, Level.ExplosionInteraction.BLOCK);
                    }
                }
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
