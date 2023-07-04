package com.tutorial.crystal.item;

import com.tutorial.crystal.Entity.Danmaku;
import com.tutorial.crystal.init.EntityTypeRegistry;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemHakureiGohei extends BowItem {
    public ItemHakureiGohei() {
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
                for (int j = 0; j < 9; j++) {
                    int angle = 8 * j - 32;
                    Danmaku danmaku = new Danmaku(EntityTypeRegistry.DANMAKU.get(),level,0.5F);
                    danmaku.moveTo(living.getX(),living.getY() + 1,living.getZ());
                    danmaku.shootFromRotation(living,living.getXRot(),living.getYRot() + angle,0, f,1);
                    level.addFreshEntity(danmaku);
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
