package com.tutorial.lively_danmaku.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;

import static com.tutorial.lively_danmaku.gui.screen.DanmakuImportScreen.DANMAKU_IMAGE;

public class MathMethod {

    public static ArrayList<Long> mergePoint (ArrayList<ColorPoint> pointArrayList) {
        ArrayList<Long> merged = new ArrayList<>();
        for (ColorPoint point : pointArrayList) {
            merged.add(merge((int) point.x, (int) point.y,point.color));
        }
        return merged;
    }

    public static long merge(int value1, int value2, int color) {
        value1 &= 0xFFF; // 确保 value1 在 0 到 4095 之间
        value2 &= 0xFFF; // 确保 value2 在 0 到 4095 之间

        long pos = 0;
        pos |= ((long) value1 << 12);
        pos |= value2;
        return (pos << 32) | (color & 0xFFFFFFFFL);
    }

    public static ColorPoint extract(long value) {
        int scale = -10;
        long pos = (value & 0xFFFFFFFF00000000L) >>> 32;
        int color = (int) (value & 0xFFFFFFFFL);
        short y = (short) (pos & 0xFFF); // 获取低 12 位作为 y 值
        short x = (short) ((pos >> 12) & 0xFFF); // 获取高 12 位作为 x 值
        return new ColorPoint((double) x / scale, (double) y / scale, 0, color);
    }

    public static void exportImage(ArrayList<ArrayList<Point>> arrayList) {
        ArrayList<Point> flattenedList = arrayList.stream()
                .flatMap(ArrayList::stream)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        int maxX = flattenedList.stream().max(Comparator.comparingInt(point -> point.x)).map(point -> point.x).orElse(64);
        int maxY = flattenedList.stream().max(Comparator.comparingInt(point -> point.y)).map(point -> point.y).orElse(64);
        int minX = flattenedList.stream().min(Comparator.comparingInt(point -> point.x)).map(point -> point.x).orElse(0);
        int minY = flattenedList.stream().min(Comparator.comparingInt(point -> point.y)).map(point -> point.y).orElse(0);
        arrayList.forEach(pointArrayList -> pointArrayList.forEach(point -> {
            point.x -= minX;
            point.y -= minY;
        }));
        BufferedImage image = new BufferedImage(maxX - minX,maxY - minY,BufferedImage.TYPE_INT_BGR);
        Path outputPath = DANMAKU_IMAGE.resolve("output.png");
        Graphics2D graphics2D = image.createGraphics();
        arrayList.forEach(pointArrayList -> drawLines(graphics2D,pointArrayList));
        exportImage(image,outputPath.toString());
        graphics2D.dispose();
        arrayList.forEach(pointArrayList -> pointArrayList.forEach(point -> {
            point.x += minX;
            point.y += minY;
        }));
    }

    private static void drawLines(Graphics2D g2d, ArrayList<Point> points) {
        g2d.setColor(Color.WHITE);
        for (int i = 0; i < points.size() - 1; i++) {
            Point startPoint = points.get(i);
            Point endPoint = points.get(i + 1);
            g2d.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
        }
    }

    private static void exportImage(BufferedImage image, String fileName) {
        try {
            File output = new File(fileName);
            ImageIO.write(image, "png", output);
            System.out.println("Image exported successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
