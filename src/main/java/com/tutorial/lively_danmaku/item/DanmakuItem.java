package com.tutorial.lively_danmaku.item;

import com.tutorial.lively_danmaku.entity.NormalDanmaku;
import com.tutorial.lively_danmaku.init.EntityTypeRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;
import java.util.Random;

public class DanmakuItem extends Item {
    public DanmakuItem(Properties p_41383_) {
        super(p_41383_);
    }
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand interactionHand) {
        ItemStack itemstack = player.getItemInHand(interactionHand);
        if (!level.isClientSide) {
            Color color;
            if (itemstack.getOrCreateTag().get("danmaku_color") != null) {
                color = new Color(Integer.parseInt(String.valueOf(itemstack.getOrCreateTag().get("danmaku_color"))));
            } else {
                Random random = new Random();
                color = new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255));
            }
            NormalDanmaku danmaku = new NormalDanmaku(EntityTypeRegistry.DANMAKU.get(),level,0.5F,color);
            danmaku.moveTo(player.getX(),player.getY() + 0.6,player.getZ());
            danmaku.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            level.addFreshEntity(danmaku);
        }

        if (!player.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    public int getColor(ItemStack itemStack) {
        if (itemStack.getOrCreateTag().get("danmaku_color") != null) {
            return Integer.parseInt(String.valueOf(itemStack.getOrCreateTag().get("danmaku_color")));
        }
        else return Color.PINK.getRGB();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flags) {
        super.appendHoverText(stack, level, list, flags);
        if (level != null && stack.getOrCreateTag().get("danmaku_color") != null) {
            Color color = new Color(Integer.parseInt(String.valueOf(stack.getOrCreateTag().get("danmaku_color"))));
            list.add(Component.translatable("block.danmaku_import.red").append(String.valueOf(color.getRed())).withStyle(ChatFormatting.RED));
            list.add(Component.translatable("block.danmaku_import.green").append(String.valueOf(color.getGreen())).withStyle(ChatFormatting.GREEN));
            list.add(Component.translatable("block.danmaku_import.blue").append(String.valueOf(color.getBlue())).withStyle(ChatFormatting.BLUE));
        }
    }
}
