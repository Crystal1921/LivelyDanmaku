package com.tutorial.lively_danmaku.gui.widget;

import com.tutorial.lively_danmaku.gui.screen.DanmakuColorScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;
import org.jetbrains.annotations.NotNull;

public class ColorSlider extends AbstractWidget {
    public int number;
    private EditBox editBox;
    public ColorSlider(int pX, int pY, EditBox editBox) {
        super(pX, pY, 64, 10, Component.empty());
        this.editBox = editBox;
    }

    public void onClick(double pMouseX, double pMouseY) {
        this.number = (int) (pMouseX - this.getX());
        this.editBox.setValue(String.valueOf(number * 4));
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        pGuiGraphics.fill(this.getX(),this.getY() + 4,this.getX() + 64,this.getY() + 6, FastColor.ARGB32.color(255,50,50,50));
        pGuiGraphics.fill(this.getX() + number - 2,this.getY() + 2,this.getX() + number + 2,this.getY() + 8,FastColor.ARGB32.color(255,0,0,0));
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput pNarrationElementOutput) {
    }
}
