package com.tutorial.lively_danmaku.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class ColorBlockItem extends BlockItem {
    private static final ChatFormatting[] RAINBOW = {ChatFormatting.RED,ChatFormatting.GOLD,ChatFormatting.YELLOW,ChatFormatting.GREEN,ChatFormatting.AQUA,ChatFormatting.LIGHT_PURPLE,ChatFormatting.DARK_PURPLE};
    public ColorBlockItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }
    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flags) {
        super.appendHoverText(stack, level, list, flags);
        if (level != null) {
            long time = level.getGameTime();
            list.add(Component.translatable("item.lively_danmaku.creative_item").withStyle(RAINBOW[(int) ((time / 2) % 7)]));
        }
    }
}
