package com.tutorial.lively_danmaku.gui.screen;

import com.tutorial.lively_danmaku.gui.menu.AdvancedDanmakuMenu;
import com.tutorial.lively_danmaku.gui.widget.PaintWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.StateSwitchingButton;
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
        this.addPaintButton();
        this.addGridButton();
        this.addWithdrawButton();
        this.addExportButton();
    }

    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        double d0 = mouseX + 4 - i;
        double d1 = mouseY - 44 - j;
        if (this.minecraft != null && this.minecraft.player != null && this.minecraft.gameMode != null) {
            if (d0 >= 0D && d1 >= 0.0D && d0 < 30D && d1 < 30D && this.menu.clickMenuButton(this.minecraft.player, 0)) {
                this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, 0);
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
        if (this.menu.isFull[0] == 0) {
            guiGraphics.blit(DANMAKU_TABLE, i - 7, j + 41, 175, 30,30,30);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        pGuiGraphics.drawString(this.font, this.title, this.titleLabelX - 80, this.titleLabelY, 4210752, false);
        pGuiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX - 80, this.inventoryLabelY, 4210752, false);
    }

    public void render(@NotNull GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(guiGraphics, pMouseX, pMouseY);
    }

    public void resize(@NotNull Minecraft pMinecraft, int pWidth, int pHeight) {
        ArrayList<ArrayList<Point>> pointList = paintWidget.getPointList();
        this.init(pMinecraft, pWidth, pHeight);
        this.paintWidget.setPointList(pointList);
    }

    private void addPaintButton() {
        StateSwitchingButton paint = new StateSwitchingButton(this.leftPos + 60, this.topPos + 6, 16, 16, this.paintWidget.isPaint) {
            @Override
            public void onClick(double mouseX, double mouseY) {
                paintWidget.isPaint = !paintWidget.isPaint;
                if (paintWidget.isPaint) {
                    paintWidget.addList();
                }
                this.isStateTriggered = !this.isStateTriggered;
            }
        };
        paint.initTextureValues(176,60,16,16,DANMAKU_TABLE);
        this.addRenderableWidget(paint);
    }

    private void addGridButton() {
        StateSwitchingButton grid = new StateSwitchingButton(this.leftPos + 60, this.topPos + 24, 16, 16, this.paintWidget.isGrid) {
            @Override
            public void onClick(double mouseX, double mouseY) {
                paintWidget.isGrid = !paintWidget.isGrid;
                this.isStateTriggered = !this.isStateTriggered;
            }
        };
        grid.initTextureValues(176,92,16,16,DANMAKU_TABLE);
        this.addRenderableWidget(grid);
    }

    private void addWithdrawButton() {
        StateSwitchingButton withdraw = new StateSwitchingButton(this.leftPos + 60, this.topPos + 42,16, 16, this.paintWidget.isWithdraw) {
            @Override
            public void onClick(double mouseX, double mouseY) {
                paintWidget.withdraw();
            }
        };
        withdraw.initTextureValues(176,124,16,16,DANMAKU_TABLE);
        this.addRenderableWidget(withdraw);
    }

    private void addExportButton() {
        StateSwitchingButton export = new StateSwitchingButton(this.leftPos + 60, this.topPos + 60, 16, 16, this.paintWidget.isExport) {
            @Override
            public void onClick(double mouseX, double mouseY) {
                paintWidget.export();
            }
        };
        export.initTextureValues(176,156,16,16,DANMAKU_TABLE);
        this.addRenderableWidget(export);
    }

    @Override
    public int getGuiLeft() {
        return leftPos + 80;
    }
}
