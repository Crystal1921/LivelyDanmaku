package com.tutorial.lively_danmaku.BlockEntity;

import com.tutorial.lively_danmaku.GUI.AdvancedDanmakuScreen;
import com.tutorial.lively_danmaku.mixin.mixinInterface.GuiGraphicsInterface;
import com.tutorial.lively_danmaku.network.DanmakuNetwork;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public class PaintWidget extends AbstractWidget {
    private final ArrayList<ArrayList<Point>> pointList = new ArrayList<>();
    private int number = 0;
    private static final int TRANSLUCENT_BLACK = 838860800;
    private final AdvancedDanmakuScreen screen;

    public PaintWidget(int pX, int pY, AdvancedDanmakuScreen screen) {
        super(pX, pY, 175,175, Component.empty());
        this.screen = screen;
        pointList.add(new ArrayList<>());
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
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


    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput pNarrationElementOutput) {

    }

    @Override
    public void onClick(double x, double y) {
        if (screen.getMenu().isPaint[0] == 0) {
            System.out.println(x);
            System.out.println(y);
            pointList.get(number).add(new Point((int)x,(int)y));
            DanmakuNetwork.PointPacket packet = new DanmakuNetwork.PointPacket(screen.getMenu().containerId, (short) (x - 360.5) ,(short) (y - 125.5) ,(byte) number);
            DanmakuNetwork.CHANNEL_POINT.sendToServer(packet);
        }
    }

    public void addList () {
        this.pointList.add(new ArrayList<>());
        this.number++;
    }
}
