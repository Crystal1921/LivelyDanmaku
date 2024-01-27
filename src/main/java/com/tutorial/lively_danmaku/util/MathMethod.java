package com.tutorial.lively_danmaku.util;

import java.awt.*;
import java.util.ArrayList;

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
}
