package com.tutorial.lively_danmaku.item;

import com.tutorial.lively_danmaku.Entity.Danmaku;
import com.tutorial.lively_danmaku.Entity.StarDanmaku;
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
                int amount = item.getOrCreateTag().getInt("amount");
                int speed = item.getOrCreateTag().getInt("speed");
                float multi = 1 + (float) log((float) speed / 5 + 1);
                int k = this.getUseDuration(item) - i;
                k = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(item, level, player, k, true);
                if (k <= 10) k = 10;
                float f = getPowerForTime(k);
                if (item.getOrCreateTag().get("distribution") == null) {
                    for (int j = 0; j < 9; j++) {
                        int angle = 8 * j - 32;
                        Danmaku danmaku = new Danmaku(EntityTypeRegistry.DANMAKU.get(),level,0.5F);
                        danmaku.moveTo(living.getX(),living.getY() + 1,living.getZ());
                        danmaku.shootFromRotation(living,living.getXRot(),living.getYRot() + angle,0, f * multi,1);
                        level.addFreshEntity(danmaku);
                    }
                }   else{
                    String distribution = String.valueOf(item.getOrCreateTag().get("distribution"));
                    int[][] array = String2Int(distribution);
                    int num = countOccurrences(array);
                    int repeat = amount / num;
                    if (num > amount) {
                        randomizeArray(array, num - amount);
                        repeat = 1;
                    }
                    for (int m = 0; m < repeat; m++) {
                        for (int j = 0; j < 9; j++) {
                            for (int l = 0; l < 9; l++) {
                                if(array[j][l] == 1) {
                                    Danmaku danmaku = new Danmaku(EntityTypeRegistry.DANMAKU.get(),level,0.5F);
                                    danmaku.moveTo(living.getX(),living.getY() + 1,living.getZ());
                                    danmaku.shootFromRotation(living,living.getXRot() + 16 - j * 4,living.getYRot() + 16 - l * 4,0, f * multi,1);
                                    level.addFreshEntity(danmaku);
                                } else if (array[j][l] == 2) {
                                    StarDanmaku danmaku = new StarDanmaku(EntityTypeRegistry.STAR_DANMAKU.get(), level);
                                    danmaku.moveTo(living.getX(),living.getY() + 1,living.getZ());
                                    danmaku.shootFromRotation(living,living.getXRot() + 16 - j * 4,living.getYRot() + 16 - l * 4,0, f * multi,1);
                                    level.addFreshEntity(danmaku);
                                }
                            }
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
    @SuppressWarnings("all")
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flags) {
        super.appendHoverText(stack, level, list, flags);
        list.add(Component.translatable("hakurei_gohei.speed").withStyle(ChatFormatting.GRAY).append(" : " + stack.getOrCreateTag().getInt("speed")));
        list.add(Component.translatable("hakurei_gohei.amount").withStyle(ChatFormatting.GRAY).append(" : " + stack.getOrCreateTag().getInt("damage")));
        if (stack.getOrCreateTag().get("distribution") != null) {
            for (int i = 0; i < 9; i++) {
                list.add(Component.translatable(Component.EMPTY.getString()).append(stack.getOrCreateTag().get("distribution").toString().substring(i * 9 + 1, i * 9 + 10)));
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

    public static void randomizeArray(int[][] array, int numOfOnesToChange) {
        int count = 0;
        int rows = array.length;
        int columns = array[0].length;
        Random random = new Random();

        while (count < numOfOnesToChange) {
            int randomRow = random.nextInt(rows);
            int randomColumn = random.nextInt(columns);

            if (array[randomRow][randomColumn] == 1 || array[randomRow][randomColumn] == 2) {
                array[randomRow][randomColumn] = 0;
                count++;
            }
        }
    }
}
