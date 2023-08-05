package com.tutorial.lively_danmaku.item;

import com.tutorial.lively_danmaku.Entity.Danmaku;
import com.tutorial.lively_danmaku.init.EntityTypeRegistry;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class BulletDanmakuItem extends Item {
    public BulletDanmakuItem(Properties properties) {
        super(properties);
    }

    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand interactionHand) {
        ItemStack itemstack = player.getItemInHand(interactionHand);
        if (!level.isClientSide) {
            Danmaku danmaku = new Danmaku(EntityTypeRegistry.HAKUREI_BULLET.get(), level,0.5F);
            danmaku.moveTo(player.getX(),player.getY() + 0.6,player.getZ());
            danmaku.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 0.5F, 1.0F);
            level.addFreshEntity(danmaku);
        }

        if (!player.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}
