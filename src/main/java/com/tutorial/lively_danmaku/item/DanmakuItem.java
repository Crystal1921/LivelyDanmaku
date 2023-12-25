package com.tutorial.lively_danmaku.item;

import com.tutorial.lively_danmaku.entity.NormalDanmaku;
import com.tutorial.lively_danmaku.init.EntityTypeRegistry;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class DanmakuItem extends Item {
    public DanmakuItem(Properties p_41383_) {
        super(p_41383_);
    }
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand interactionHand) {
        ItemStack itemstack = player.getItemInHand(interactionHand);
        if (!level.isClientSide) {
            NormalDanmaku danmaku = new NormalDanmaku(EntityTypeRegistry.DANMAKU.get(),level,0.5F);
            danmaku.moveTo(player.getX(),player.getY() + 0.6,player.getZ());
            danmaku.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            level.addFreshEntity(danmaku);
        }

        if (!player.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}
