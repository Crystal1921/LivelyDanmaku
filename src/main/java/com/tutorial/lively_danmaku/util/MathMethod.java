package com.tutorial.lively_danmaku.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;

import static com.tutorial.lively_danmaku.gui.DanmakuImportScreen.DANMAKU_IMAGE;

public class MathMethod {

    public static ArrayList<Point> extractPoint (ArrayList<Long> longs) {
        ArrayList<Point> pointArrayList = new ArrayList<>();
        for (Long value : longs) {
            pointArrayList.add(extract(value));
        }
        return pointArrayList;
    }

    public static ArrayList<Long> mergePoint (ArrayList<Point> pointArrayList) {
        ArrayList<Long> merged = new ArrayList<>();
        for (Point point : pointArrayList) {
            merged.add(merge(point.x,point.y));
        }
        return merged;
    }

    public static long merge(int value1, int value2) {
        value1 &= 0b1111111111; // 确保 value1 在 0 到 1023 之间
        value2 &= 0b1111111111; // 确保 value2 在 0 到 1023 之间

        long result = 0L;
        result |= (long) value1 << 10;
        result |= value2;
        return result;
    }

    public static Point extract(long value) {
        int y = (int) (value & 0b1111111111); // 获取低 10 位作为 y 值
        int x = (int) ((value >> 10) & 0b1111111111); // 获取高 10 位作为 x 值
        return new Point(x, y);
    }

    public static String PointList (ArrayList<Point> pointArrayList) {
        StringBuilder stringBuilder = new StringBuilder();
        pointArrayList.forEach(point -> stringBuilder.append("*").append(point.x).append("+").append(point.y));
        return stringBuilder.toString();
    }

    public static void exportImage(ArrayList<ArrayList<Point>> arrayList) {
        ArrayList<Point> flattenedList = arrayList.stream()
                .flatMap(ArrayList::stream)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        int maxX = flattenedList.stream().max(Comparator.comparingInt(point -> point.x)).map(point -> point.x).orElse(0);
        int maxY = flattenedList.stream().max(Comparator.comparingInt(point -> point.y)).map(point -> point.y).orElse(64);
        int minX = flattenedList.stream().min(Comparator.comparingInt(point -> point.x)).map(point -> point.x).orElse(0);
        int minY = flattenedList.stream().min(Comparator.comparingInt(point -> point.y)).map(point -> point.y).orElse(64);
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
