package com.tutorial.lively_danmaku.gui.widget;

import com.tutorial.lively_danmaku.gui.DanmakuImportScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ImageWidget extends AbstractWidget {
    private ResourceLocation resourceLocation;
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
    }

    public void setResourceLocation (ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput pNarrationElementOutput) {
    }
}
