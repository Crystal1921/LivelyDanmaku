package com.tutorial.lively_danmaku.gui.widget;

import com.tutorial.lively_danmaku.gui.screen.DanmakuImportScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public class ImageWidget extends AbstractWidget {
    private ResourceLocation resourceLocation;
    public ArrayList<Point> pointList;
    private final DanmakuImportScreen screen;
    public ImageWidget(int pX, int pY, DanmakuImportScreen screen) {
        super(pX, pY, 0, 0, Component.empty());
        this.screen = screen;
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        if (this.resourceLocation == null) {
            pGuiGraphics.drawString(screen.getFontRenderer(),Component.translatable("ui.danmaku_import.npe"),this.getX() + 55,this.getY() + 25,0xFFFFFF);
        }   else {
            pGuiGraphics.blit(resourceLocation,this.getX() + 60, this.getY() + 5,0,0,0,40,40,40,40);
        }
        if (this.pointList != null) {
            pointList.forEach(point -> pGuiGraphics.fill(this.getX() + 100 + point.x, this.getY() + 5 + point.y, this.getX() + 101 + point.x, this.getY() + 7 + point.y, Color.WHITE.getRGB()));
        }
    }

    public void setResourceLocation (ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput pNarrationElementOutput) {
    }
}
