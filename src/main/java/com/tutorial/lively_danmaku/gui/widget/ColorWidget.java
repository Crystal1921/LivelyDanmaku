package com.tutorial.lively_danmaku.gui.widget;

import com.tutorial.lively_danmaku.gui.screen.DanmakuColorScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class ColorWidget extends AbstractWidget {
    public int color = FastColor.ARGB32.color(255,0,0,0);
    public ColorWidget(int pX, int pY) {
        super(pX, pY, 20,20, Component.empty());
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        pGuiGraphics.fill(getX(),getY(),getX() + 20,getY() + 20, color);
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput pNarrationElementOutput) {
    }

    public void setColor(int red, int green, int blue){
        this.color = FastColor.ARGB32.color(255,red,green,blue);
    }
}
