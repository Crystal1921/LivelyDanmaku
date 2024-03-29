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

public class ImageUtils {
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

    public static void drawLines(Graphics2D g2d, ArrayList<Point> points) {
        g2d.setColor(Color.WHITE);
        for (int i = 0; i < points.size() - 1; i++) {
            Point startPoint = points.get(i);
            Point endPoint = points.get(i + 1);
            g2d.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
        }
    }

    public static void exportImage(BufferedImage image, String fileName) {
        try {
            File output = new File(fileName);
            ImageIO.write(image, "png", output);
            System.out.println("Image exported successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
