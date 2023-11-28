package com.tutorial.lively_danmaku.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public class AdvancedDanmakuScreen extends AbstractContainerScreen<AdvancedDanmakuMenu> {
    private static final ResourceLocation DANMAKU_TABLE = new ResourceLocation("lively_danmaku", "textures/gui/danmaku_table.png");
    private static final ResourceLocation ADVANCED_DANMAKU_TABLE = new ResourceLocation("lively_danmaku", "textures/gui/advanced_danmaku_table.png");
    private PaintWidget paintWidget;
    public AdvancedDanmakuScreen(AdvancedDanmakuMenu danmakuMenu, Inventory inventory, Component component) {
        super(danmakuMenu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.paintWidget = this.addRenderableWidget(new PaintWidget(i + 120,j - 5,this));
    }

    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        double d0 = mouseX + 4 - i;
        double d1 = mouseY - 44 - j;

        if (this.minecraft != null && this.minecraft.player != null && this.minecraft.gameMode != null) {
            if (d0 >= 0D && d1 >= 0.0D && d0 < 30D && d1 < 30D && this.menu.clickMenuButton(this.minecraft.player, 0)) {
                this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, 0);
            } else if ( d0 >= 67 && d1 >= -36 && d0 < 97 && d1 < -6 && this.menu.clickMenuButton(this.minecraft.player, 1)) {
                this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, 1);
                if (menu.isPaint[0] == 0) paintWidget.addList();
            } else if (d0 >= 67 && d1 >= -6 && d0 < 97 && d1 < 26 && this.menu.clickMenuButton(this.minecraft.player, 2)) {
                this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, 2);
                paintWidget.isGrid = !paintWidget.isGrid;
            }
        }

        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float p_97788_, int p_97789_, int p_97790_) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(DANMAKU_TABLE, i - 80 , j, 0, 0, this.imageWidth, this.imageHeight);
        guiGraphics.blit(ADVANCED_DANMAKU_TABLE, i + 120, j - 4, 0, 0, this.imageWidth - 1, this.imageHeight + 10);
        if (this.menu.isPaint[0] == 1) {
            guiGraphics.blit(DANMAKU_TABLE, i + 60, j + 5, 175, 90, 30, 30);
        }else {
            guiGraphics.blit(DANMAKU_TABLE, i + 60, j + 5, 175, 60, 30, 30);
        }
        if (this.menu.isGrid[0] == 1) {
            guiGraphics.blit(DANMAKU_TABLE, i + 60, j + 35, 175, 150, 30, 30);
        }else {
            guiGraphics.blit(DANMAKU_TABLE, i + 60, j + 35, 175, 120, 30, 30);
        }
        if (this.menu.isFull[0] == 0) {
            guiGraphics.blit(DANMAKU_TABLE, i - 7, j + 41, 175, 30,30,30);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        pGuiGraphics.drawString(this.font, this.title, this.titleLabelX - 80, this.titleLabelY, 4210752, false);
        pGuiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX - 80, this.inventoryLabelY, 4210752, false);
    }

    public void render(@NotNull GuiGraphics guiGraphics, int p_282491_, int p_281953_, float p_282182_) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, p_282491_, p_281953_, p_282182_);
        this.renderTooltip(guiGraphics, p_282491_, p_281953_);
    }
    public void resize(Minecraft pMinecraft, int pWidth, int pHeight) {
        ArrayList<ArrayList<Point>> pointList = paintWidget.getPointList();
        this.init(pMinecraft, pWidth, pHeight);
        this.paintWidget.setPointList(pointList);
    }
}
