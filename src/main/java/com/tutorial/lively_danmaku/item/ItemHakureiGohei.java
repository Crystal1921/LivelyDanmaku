package com.tutorial.lively_danmaku.item;

import com.tutorial.lively_danmaku.entity.NormalDanmaku;
import com.tutorial.lively_danmaku.entity.StarDanmaku;
import com.tutorial.lively_danmaku.init.EntityTypeRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
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

import static java.lang.Math.log;

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
                if (f > 0.8) HakureiShoot(item, level, player);
            }
        }
    }

    private void HakureiShoot(@NotNull ItemStack item, Level level, Player player) {
        this.HakureiShoot(item,level,player,player.getX(),player.getY(),player.getZ(),player.getXRot(),player.getYRot(),true);
    }

    public void HakureiShoot(@NotNull ItemStack item, Level level, Player player,double getX,double getY, double getZ, float getXRot, float getYRot, boolean isWiggle) {
        if (item.getOrCreateTag().get("crystal_distribution") == null) {
            for (int j = 0; j < 9; j++) {
                int angle = 8 * j - 32;
                NormalDanmaku danmaku = new NormalDanmaku(EntityTypeRegistry.DANMAKU.get(), level);
                danmaku.setOwner(player);
                danmaku.moveTo(getX, getY + 1, getZ);
                danmaku.shootFromRotation(getXRot, getYRot + angle, 0, 1, 1,isWiggle);
                level.addFreshEntity(danmaku);
            }
        } else {
            String distribution = String.valueOf(item.getOrCreateTag().get("crystal_distribution"));
            String rgb = String.valueOf(item.getOrCreateTag().get("crystal_color"));
            int amount = item.getOrCreateTag().getInt("crystal_amount");
            int speed = item.getOrCreateTag().getInt("crystal_speed");
            float multi = 1 + (float) log((float) speed / 5 + 1);
            int[][] position = String2Int(distribution);
            int[] color = String2Color(rgb);
            int num = countOccurrences(position);
            int repeat = amount / num;
            if (num > amount) {
                repeat = 1;
            }
            for (int m = 0; m < repeat; m++) {
                int count = 0;
                for (int j = 0; j < 9; j++) {
                    for (int l = 0; l < 9; l++) {
                        if (position[j][l] == 1) {
                            NormalDanmaku danmaku = new NormalDanmaku(EntityTypeRegistry.DANMAKU.get(), level, 0.5F, new Color(color[count]));
                            danmaku.setOwner(player);
                            danmaku.moveTo(getX, getY + 1, getZ);
                            danmaku.shootFromRotation((getXRot - 16 + j * 4), (getYRot - 16 + l * 4), 0, multi, 1, isWiggle);
                            level.addFreshEntity(danmaku);
                            count++;
                        } else if (position[j][l] == 2) {
                            StarDanmaku danmaku = new StarDanmaku(EntityTypeRegistry.STAR_DANMAKU.get(), level);
                            danmaku.setOwner(player);
                            danmaku.moveTo(getX, getY + 1, getZ);
                            danmaku.shootFromRotation((getXRot - 16 + j * 4), (getYRot - 16 + l * 4), 0, multi, 1, isWiggle);
                            level.addFreshEntity(danmaku);
                        }
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
    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flags) {
        super.appendHoverText(stack, level, list, flags);
        list.add(Component.translatable("hakurei_gohei.speed").withStyle(ChatFormatting.YELLOW).append(" : " + stack.getOrCreateTag().getInt("crystal_speed")));
        list.add(Component.translatable("hakurei_gohei.amount").withStyle(ChatFormatting.GOLD).append(" : " + stack.getOrCreateTag().getInt("crystal_amount")));
        if (stack.getOrCreateTag().get("crystal_distribution") != null) {
            for (int i = 0; i < 9; i++) {
                list.add(Component.translatable(Component.EMPTY.getString()).append(format_NBT(stack.getOrCreateTag().get("crystal_distribution").toString().substring(i * 9 + 1, i * 9 + 10))));
            }
        }
    }
    public static int[][] String2Int (String string) {
        int[][] array = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char charAt = string.charAt(i * 9 + j + 1);
                if (charAt == '1') {
                    array[i][j] = 1;
                } else if (charAt == '2') array[i][j] = 2;
            }
        }
        return array;
    }

    public static int[] String2Color(String string) {
        int[] array = new int[81];
        String newString = string.replace("\"", "");
        String[] parts = newString.split("#");

        for (int i = 0; i < parts.length && i < array.length; i++) {
            array[i] = Integer.parseInt(parts[i]);
        }
        return array;
    }

    public static int countOccurrences(int[][] array) {
        int count = 0;
        for (int[] row : array) {
            for (int element : row) {
                if (element == 1 || element == 2) {
                    count++;
                }
            }
        }
        return count;
    }

    public String format_NBT(String string) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            switch (string.charAt(i)){
                case '#' -> result.append('\u3000');
                case '1' -> result.append('●').append(' ');
                case '2' -> result.append('★');
            }
        }
        return result.toString();
    }
}
