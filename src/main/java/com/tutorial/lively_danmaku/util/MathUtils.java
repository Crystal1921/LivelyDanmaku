package com.tutorial.lively_danmaku.util;

import com.ezylang.evalex.EvaluationException;
import com.ezylang.evalex.Expression;
import com.ezylang.evalex.data.EvaluationValue;
import com.ezylang.evalex.parser.ParseException;

import java.util.ArrayList;

public class MathUtils {

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

    public static void main(String[] args) throws EvaluationException, ParseException {
        Expression expression = new Expression("1 + 2 / (4 * SQRT(4))");

        EvaluationValue result = expression.evaluate();

        System.out.println(result.getNumberValue()); // prints 1.25
    }
}
