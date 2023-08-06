package com.tutorial.lively_danmaku.item;

import com.tutorial.lively_danmaku.Entity.HakureiDanmaku;
import com.tutorial.lively_danmaku.init.EntityTypeRegistry;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class HakureiDanmakuItem extends Item {
    public HakureiDanmakuItem(Properties properties) {
        super(properties);
    }

    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand interactionHand) {
        ItemStack itemstack = player.getItemInHand(interactionHand);
        if (!level.isClientSide) {
            HakureiDanmaku danmaku = new HakureiDanmaku(EntityTypeRegistry.HAKUREI_BULLET.get(), level,true);
            danmaku.setOwner(player);
            danmaku.moveTo(player.getX(),player.getY() + 0.6,player.getZ());
            danmaku.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 0.4F, 1.0F);
            level.addFreshEntity(danmaku);
        }

        if (!player.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}
