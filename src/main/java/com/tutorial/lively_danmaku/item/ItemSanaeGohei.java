package com.tutorial.lively_danmaku.item;

import com.tutorial.lively_danmaku.entity.FiveStarEmitter;
import com.tutorial.lively_danmaku.init.EntityTypeRegistry;
import com.tutorial.lively_danmaku.util.ColorPoint;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.lang.Math.*;

public class ItemSanaeGohei extends BowItem {
    private FiveStarEmitter emitter;
    private final ArrayList<ColorPoint> five_star = new ArrayList<>();
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
            emitter.isShoot = true;
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        if (!level.isClientSide) {
            SanaeShoot(level,itemstack,player.getXRot(),player.getYRot(),player.getX(),player.getY(),player.getZ());
        }
        return InteractionResultHolder.consume(itemstack);
    }

    public void SanaeShoot(@NotNull Level level, ItemStack itemstack, float XRot, float YRot, double positionX, double positionY, double positionZ) {
        ArrayList<ColorPoint> list;
        String type;
        if (itemstack.getOrCreateTag().get("crystal_point") != null){
            list = viewTransform(stringToPointList(String.valueOf(itemstack.getOrCreateTag().get("crystal_point"))), XRot, YRot);
        }   else {
            list = viewTransform(five_star, XRot, YRot);
        }
        if (itemstack.getOrCreateTag().get("crystal_type") != null) {
            type = Objects.requireNonNull(itemstack.getOrCreateTag().get("crystal_type")).getAsString();
        }   else {
            type = "danmaku";
        }
        emitter = new FiveStarEmitter(EntityTypeRegistry.FIVE_STAR_EMITTER.get(), level, list, XRot, YRot, type);
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

    private static ArrayList<ColorPoint> stringToPointList(String pointString) {
        ArrayList<ColorPoint> points = new ArrayList<>();
        String newString = pointString.replace("\"","");
        String[] parts = newString.split("#");
        for (String part : parts) {
            String[] coordinates = part.split("\\+");
            if (coordinates.length == 3) {
                double x = Double.parseDouble(coordinates[0]);
                double y = Double.parseDouble(coordinates[1]);
                int color = (int) Double.parseDouble(coordinates[2]);
                points.add(new ColorPoint(-x / 10, -y / 10, 0,color));
            }
        }
        return points;
    }

    private ArrayList<ColorPoint> viewTransform(ArrayList<ColorPoint> origin, float XRot, float YRot) {
        return origin.stream()
                .map(point -> point.transform(XRot,YRot,6))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
