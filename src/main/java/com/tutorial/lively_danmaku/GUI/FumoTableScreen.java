package com.tutorial.lively_danmaku.GUI;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class FumoTableScreen extends AbstractContainerScreen<FumoTableMenu> {
    private static final ResourceLocation FUMO_TABLE = new ResourceLocation("lively_danmaku", "textures/gui/fumo_table.png");
    public FumoTableScreen(FumoTableMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        pGuiGraphics.blit(FUMO_TABLE, i , j, 0, 0, this.imageWidth, this.imageHeight);
    }
}
