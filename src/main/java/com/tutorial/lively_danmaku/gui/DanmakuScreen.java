package com.tutorial.lively_danmaku.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class DanmakuScreen extends AbstractContainerScreen<DanmakuMenu> {
    private static final ResourceLocation DANMAKU_TABLE = new ResourceLocation("lively_danmaku", "textures/gui/danmaku_table.png");
    private static final ResourceLocation DANMAKU_TABLE_9 = new ResourceLocation("lively_danmaku", "textures/gui/danmaku_table_9_9.png");
    public DanmakuScreen(DanmakuMenu danmakuMenu, Inventory inventory, Component component) {
        super(danmakuMenu, inventory, component);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        double d0 = mouseX + 4 - i;
        double d1 = mouseY - 44 - j;

        if (this.minecraft != null && this.minecraft.player != null && d0 >= 0D && d1 >= 0.0D && d0 < 30D && d1 < 30D && this.menu.clickMenuButton(this.minecraft.player, 0)) {
            if (this.minecraft.gameMode != null) {
                this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, 0);
            }
            return true;
        }

        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(DANMAKU_TABLE, i - 80 , j, 0, 0, this.imageWidth, this.imageHeight);
        guiGraphics.blit(DANMAKU_TABLE_9, i + 120, j - 4, 0, 0, this.imageWidth - 1, this.imageHeight + 10);
        if (this.menu.isFull[0] == 0) {
            guiGraphics.blit(DANMAKU_TABLE, i - 7, j + 41, 175, 30,30,30);
        }
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(guiGraphics, pMouseX, pMouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        pGuiGraphics.drawString(this.font, this.title, this.titleLabelX - 80, this.titleLabelY, 4210752, false);
        pGuiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX - 80, this.inventoryLabelY, 4210752, false);
    }

    @Override
    public int getGuiLeft() {
        return leftPos + 80;
    }
}
