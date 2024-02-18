package com.tutorial.lively_danmaku.util;

import java.awt.*;

import static java.lang.Math.*;

public class ColorPoint {
    public double x;
    public double y;
    public double z;
    public int color;

    public ColorPoint(double x, double y, double z, int color) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.color = color;
    }

    public ColorPoint(double x, double y, double z) {
        this(x, y, z, Color.RED.getRGB());
    }

    public Point getPoint () {
        return new Point((int) this.x, (int) this.y);
    }

    public void transformPos(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public ColorPoint transformRot(double XRot, double YRot, float distance) {
        double thetaX = toRadians(XRot);
        double thetaY = toRadians(YRot);

        double x = this.x;
        double y = this.y * sin(thetaX);
        double z = this.y * cos(thetaX);

        double newX = x * cos(thetaY) - y * sin(thetaY);
        double newY = x * sin(thetaY) + y * cos(thetaY);

        double offsetX = -sin(toRadians(YRot)) * cos(toRadians(XRot)) * distance;
        double offsetY = -sin(toRadians(XRot)) * distance + 0.8;
        double offsetZ = cos(toRadians(YRot)) * cos(toRadians(XRot)) * distance;

        return new ColorPoint(newX + offsetX, newY + offsetZ, z + offsetY, this.color);
    }
}
