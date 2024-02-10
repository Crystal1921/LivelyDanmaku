package com.tutorial.lively_danmaku.gui.widget;

import com.tutorial.lively_danmaku.gui.screen.AdvancedDanmakuScreen;
import com.tutorial.lively_danmaku.mixin.mixinInterface.GuiGraphicsInterface;
import com.tutorial.lively_danmaku.network.DanmakuNetwork;
import com.tutorial.lively_danmaku.network.PointPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

import static com.tutorial.lively_danmaku.util.MathMethod.exportImage;

public class PaintWidget extends AbstractWidget {
    private ArrayList<ArrayList<Point>> pointList = new ArrayList<>();
    private int number = 0;
    private final int gridSize = 8;
    private static final int TRANSLUCENT_BLACK = FastColor.ARGB32.color(24,256,256,256);
    private static final int TRANSLUCENT_BROWN = FastColor.ARGB32.color(128,225,190,150);
    private final AdvancedDanmakuScreen screen;
    public boolean isGrid = true;
    public boolean isPaint = true;
    public boolean isWithdraw = true;
    public boolean isExport = true;

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
    protected void updateWidgetNarration(@NotNull NarrationElementOutput pNarrationElementOutput) {}

    @Override
    public void onClick(double x, double y) {
        if (isPaint) {
            if (isGrid) {
                x = Math.round(x / gridSize) * gridSize;
                y = Math.round(y / gridSize) * gridSize;
            }
            pointList.get(number).add(new Point((int)x,(int)y));
            PointPacket packet = new PointPacket(screen.getMenu().containerId, (short) (x - 360.5) ,(short) (y - 125.5) ,(byte) number);
            DanmakuNetwork.CHANNEL.sendToServer(packet);
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

    public void withdraw() {
        int index = this.pointList.size() - 1;
        if (pointList.get(0).isEmpty()) return;
        if (!this.pointList.get(index).isEmpty()) {
            this.pointList.get(index).remove(this.pointList.get(index).size() - 1);
        } else {
            this.pointList.remove(index);
            number--;
        }
    }

    public void export() {
        exportImage(pointList);
    }
}
