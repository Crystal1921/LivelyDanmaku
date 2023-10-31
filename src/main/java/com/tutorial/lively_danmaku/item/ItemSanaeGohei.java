package com.tutorial.lively_danmaku.item;

import com.tutorial.lively_danmaku.Entity.AbstractDanmaku;
import com.tutorial.lively_danmaku.Entity.FiveStarEmitter;
import com.tutorial.lively_danmaku.init.EntityTypeRegistry;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.lang.Math.*;

public class ItemSanaeGohei extends BowItem {
    private FiveStarEmitter emitter;
    private final ArrayList<DoublePoint> five_star = new ArrayList<>();
    private static final int NUM_POINTS = 5;
    private static final int RADIUS = 5;
    public ItemSanaeGohei() {
        super(new Properties()
                .durability(500));
        double angle = PI / 2.0;
        double deltaAngle = 4.0 * PI / NUM_POINTS;
        double x1 = RADIUS * cos(angle);
        double y1 = - RADIUS * sin(angle);
        double x2 = RADIUS * cos(angle + deltaAngle);
        double y2 = - RADIUS * sin(angle + deltaAngle);
        ArrayList<DoublePoint> points = getEquallySpacedPoints(new DoublePoint(x1,y1,0), new DoublePoint(x2,y2,0), 15);
        for (int i = 0; i < NUM_POINTS; i++) {
            for (DoublePoint point : points) {
                double x = point.x;
                double y = point.y;
                double theta = toRadians(216 * i);
                five_star.add(new DoublePoint((x * cos(theta) - y * sin(theta)),(x * sin(theta) + y * cos(theta)),0));
            }
        }
    }
    @Override
    public void releaseUsing(@NotNull ItemStack item, @NotNull Level level, @NotNull LivingEntity living, int i) {
        if (emitter != null) {
            emitter.isShoot = true;
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        if (!level.isClientSide) {
            ArrayList<DoublePoint> list;
            String type;
            if (itemstack.getOrCreateTag().get("crystal_point") != null){
                list = ViewTransform(stringToPointList(String.valueOf(itemstack.getOrCreateTag().get("crystal_point"))),player);
            }   else {
                list = ViewTransform(five_star,player);
            }
            if (itemstack.getOrCreateTag().get("crystal_type") != null) {
                type = Objects.requireNonNull(itemstack.getOrCreateTag().get("crystal_type")).getAsString();
            }   else {
                type = "danmaku";
            }
                emitter = new FiveStarEmitter(EntityTypeRegistry.FIVE_STAR_EMITTER.get(), level, list, player,type);
                emitter.moveTo(player.getX(), player.getY(), player.getZ());
                level.addFreshEntity(emitter);
        }
        return InteractionResultHolder.consume(itemstack);
    }

    public static ArrayList<DoublePoint> getEquallySpacedPoints(DoublePoint point1, DoublePoint point2, int numPoints) {
        ArrayList<DoublePoint> points = new ArrayList<>();
        double deltaX = (point2.x - point1.x) / (numPoints + 1.0);
        double deltaY = (point2.y - point1.y) / (numPoints + 1.0);
        for (int i = 1; i <= numPoints; i++) {
            double x = point1.x + i * deltaX;
            double y = point1.y + i * deltaY;
            points.add(new DoublePoint(x, y, 0));
        }
        return points;
    }

    private static ArrayList<DoublePoint> stringToPointList(String pointString) {
        ArrayList<DoublePoint> points = new ArrayList<>();
        String newString = pointString.replace("\"","");
        String[] parts = newString.split("\\*");
        for (String part : parts) {
            String[] coordinates = part.split("\\+");
            if (coordinates.length == 2) {
                double x = Integer.parseInt(coordinates[0]);
                double y = Integer.parseInt(coordinates[1]);
                points.add(new DoublePoint(-x / 10, -y / 10, 0));
            }
        }
        return points;
    }

    private ArrayList<DoublePoint> ViewTransform (ArrayList<DoublePoint> origin, Player player) {
        return origin.stream()
                .map(point -> { //XoZ平面旋转
                    double x = point.x;
                    double y = point.y;
                    double theta = toRadians(player.getXRot());
                    return new DoublePoint(x, y * sin(theta), y * cos(theta));
                })
                .map(point -> { //XoY平面旋转
                    double x = point.x;
                    double y = point.y;
                    double theta = toRadians(player.getYRot());
                    return new DoublePoint((x * cos(theta) - y * sin(theta)), (x * sin(theta) + y * cos(theta)), point.z);
                })
                .map(point -> { //全部按视角平移
                    float rotationX = player.getXRot();
                    float rotationY = player.getYRot();
                    float distance = 6F;
                    double offsetX = -sin(toRadians(rotationY)) * cos(toRadians(rotationX)) * distance;
                    double offsetY = -sin(toRadians(rotationX)) * distance + 0.8;
                    double offsetZ = cos(toRadians(rotationY)) * cos(toRadians(rotationX)) * distance;
                    return new DoublePoint(point.x + offsetX, point.y + offsetZ, point.z + offsetY);
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static class DoublePoint {
        public double x;
        public double y;
        public double z;
        protected DoublePoint (double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
