package com.tutorial.lively_danmaku.gui;

import com.tutorial.lively_danmaku.mixin.mixinInterface.GuiGraphicsInterface;
import com.tutorial.lively_danmaku.network.DanmakuNetwork;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public class PaintWidget extends AbstractWidget {
    private ArrayList<ArrayList<Point>> pointList = new ArrayList<>();
    private int number = 0;
    private final int gridSize = 8;
    private static final int TRANSLUCENT_BLACK = FastColor.ARGB32.color(24,256,256,256);
    private static final int TRANSLUCENT_BROWN = FastColor.ARGB32.color(128,225,190,150);
    private final AdvancedDanmakuScreen screen;
    public boolean isGrid = true;

    public PaintWidget(int pX, int pY, AdvancedDanmakuScreen screen) {
        super(pX, pY, 175,175, Component.empty());
        this.screen = screen;
        pointList.add(new ArrayList<>());
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        if (isGrid) {
            renderLine(pGuiGraphics);
        }
        GuiGraphicsInterface guiGraphics = (GuiGraphicsInterface) pGuiGraphics;
        pointList.stream()
                .filter(innerList -> innerList.size() > 1)
                .forEach(innerList -> {
                    for (int i = 0; i < innerList.size() - 1; i++) {
                        Point p1 = innerList.get(i);
                        Point p2 = innerList.get(i + 1);
                        guiGraphics.livelyDanmaku$drawLine(p1.x, p1.y, p2.x, p2.y, 0, TRANSLUCENT_BLACK, 2);
                    }
                });
    }

    private void renderLine (@NotNull GuiGraphics guiGraphics) {
        for (int i = 0; i < 21; i++) {
            guiGraphics.hLine(this.getX() + 3,this.getX() + 172,this.getY() + (i+1) * gridSize + 4,TRANSLUCENT_BROWN);
            guiGraphics.vLine(this.getX() + (i+1) * gridSize,this.getY() + 3,this.getY() + 172,TRANSLUCENT_BROWN);
        }
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput pNarrationElementOutput) {
    }

    @Override
    public void onClick(double x, double y) {
        if (screen.getMenu().isPaint[0] == 0) {
            if (isGrid) {
                x = Math.round(x / gridSize) * gridSize;
                y = Math.round(y / gridSize) * gridSize;
            }
            pointList.get(number).add(new Point((int)x,(int)y));
            DanmakuNetwork.PointPacket packet = new DanmakuNetwork.PointPacket(screen.getMenu().containerId, (short) (x - 360.5) ,(short) (y - 125.5) ,(byte) number);
            DanmakuNetwork.CHANNEL_POINT.sendToServer(packet);
        }
    }

    public void addList () {
        this.pointList.add(new ArrayList<>());
        this.number++;
    }

    public void setPointList (ArrayList<ArrayList<Point>> pointList) {
        this.pointList = pointList;
    }

    public ArrayList<ArrayList<Point>> getPointList () {
        return this.pointList;
    }
}
