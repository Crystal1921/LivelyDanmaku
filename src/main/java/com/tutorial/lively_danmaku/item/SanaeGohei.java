package com.tutorial.lively_danmaku.item;

import com.tutorial.lively_danmaku.capability.CapabilityProvider;
import com.tutorial.lively_danmaku.entity.FiveStarEmitter;
import com.tutorial.lively_danmaku.init.EntityTypeRegistry;
import com.tutorial.lively_danmaku.util.ColorPoint;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.tutorial.lively_danmaku.util.MathMethod.extract;
import static java.lang.Math.*;

public class SanaeGohei extends BowItem {
    private FiveStarEmitter emitter;
    private final ArrayList<ColorPoint> five_star = new ArrayList<>();
    private static final int NUM_POINTS = 5;
    private static final int RADIUS = 5;
    public SanaeGohei() {
        super(new Properties()
                .durability(817));//东风谷早苗第一次登场于东方风神录中，发售于2007年8月17日
        double angle = PI / 2.0;
        double deltaAngle = 4.0 * PI / NUM_POINTS;
        double x1 = RADIUS * cos(angle);
        double y1 = - RADIUS * sin(angle);
        double x2 = RADIUS * cos(angle + deltaAngle);
        double y2 = - RADIUS * sin(angle + deltaAngle);
        ArrayList<ColorPoint> points = getEquallySpacedPoints(new ColorPoint(x1,y1,0), new ColorPoint(x2,y2,0), 15);
        for (int i = 0; i < NUM_POINTS; i++) {
            for (ColorPoint point : points) {
                double x = point.x;
                double y = point.y;
                double theta = toRadians(216 * i);
                five_star.add(new ColorPoint((x * cos(theta) - y * sin(theta)),(x * sin(theta) + y * cos(theta)),0));
            }
        }
    }
    @Override
    public void releaseUsing(@NotNull ItemStack item, @NotNull Level level, @NotNull LivingEntity living, int i) {
        if (emitter != null) {
            item.hurtAndBreak(1, living, (player1) -> player1.broadcastBreakEvent(living.getUsedItemHand()));
            emitter.isShoot = true;
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        if (!level.isClientSide) {
            AtomicReference<Float> cycleNum = new AtomicReference<>((float) 0);
            player.getCapability(CapabilityProvider.POWER).ifPresent(powerCapability -> cycleNum.set(powerCapability.getPower()));
            SanaeShoot(level,itemstack,player.getXRot(),player.getYRot(),player.getX(),player.getY(),player.getZ(),cycleNum.get());
            player.getCapability(CapabilityProvider.POWER).ifPresent(powerCapability -> powerCapability.setPower(powerCapability.getPower() - 0.02F));
        }
        return InteractionResultHolder.consume(itemstack);
    }

    public void SanaeShoot(@NotNull Level level, ItemStack itemstack, float XRot, float YRot, double positionX, double positionY, double positionZ, float cycleNum) {
        ArrayList<ColorPoint> list;
        String type;
        if (itemstack.getOrCreateTag().get("crystal_point") != null){
            CompoundTag tag = itemstack.getOrCreateTag();
            list = viewTransform(Long2ColorPoint(tag.getLongArray("crystal_point")), XRot, YRot);
        }   else {
            list = viewTransform(five_star, XRot, YRot);
        }
        if (itemstack.getOrCreateTag().get("crystal_type") != null) {
            type = Objects.requireNonNull(itemstack.getOrCreateTag().get("crystal_type")).getAsString();
        }   else {
            type = "danmaku";
        }
        emitter = new FiveStarEmitter(EntityTypeRegistry.FIVE_STAR_EMITTER.get(), level, list, XRot, YRot, type,(int) cycleNum + 1);
        emitter.moveTo(positionX, positionY, positionZ);
        level.addFreshEntity(emitter);
    }

    private static ArrayList<ColorPoint> getEquallySpacedPoints(ColorPoint point1, ColorPoint point2, int numPoints) {
        ArrayList<ColorPoint> points = new ArrayList<>();
        double deltaX = (point2.x - point1.x) / (numPoints + 1.0);
        double deltaY = (point2.y - point1.y) / (numPoints + 1.0);
        for (int i = 1; i <= numPoints; i++) {
            double x = point1.x + i * deltaX;
            double y = point1.y + i * deltaY;
            points.add(new ColorPoint(x, y, 0));
        }
        return points;
    }

    private static ArrayList<ColorPoint> Long2ColorPoint(long[] longs) {
        ArrayList<ColorPoint> points = new ArrayList<>();
        Arrays.stream(longs).forEach(aLong -> points.add(extract(aLong)));
        return points;
    }

    private ArrayList<ColorPoint> viewTransform(ArrayList<ColorPoint> origin, float XRot, float YRot) {
        Double maxX = origin.stream().max(Comparator.comparingInt(point -> (int) point.x)).map(point -> point.x).orElse(64D);
        Double maxY = origin.stream().max(Comparator.comparingInt(point -> (int) point.y)).map(point -> point.y).orElse(64D);
        Double minX = origin.stream().min(Comparator.comparingInt(point -> (int) point.x)).map(point -> point.x).orElse(0D);
        Double minY = origin.stream().min(Comparator.comparingInt(point -> (int) point.y)).map(point -> point.y).orElse(0D);
        double width = maxX + minX;
        double height = maxY + minY;
        origin.forEach(colorPoint -> colorPoint.transformPos((int) ( - width / 2), (int) ( - height / 2)));
        return origin.stream()
                .map(point -> point.transformRot(XRot,YRot,6))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
