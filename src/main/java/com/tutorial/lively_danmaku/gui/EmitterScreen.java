package com.tutorial.lively_danmaku.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class EmitterScreen extends AbstractContainerScreen<EmitterMenu> {
    private static final ResourceLocation FUMO_TABLE = new ResourceLocation("lively_danmaku", "textures/gui/fumo_table.png");
    protected EditBox commandEdit;
    public EmitterScreen(EmitterMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.commandEdit = new EditBox(this.font, this.width / 2 - 150, 50, 300, 20, Component.translatable("advMode.command"));
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        pGuiGraphics.blit(FUMO_TABLE, i , j, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int p_282491_, int p_281953_, float p_282182_) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, p_282491_, p_281953_, p_282182_);
        this.renderTooltip(guiGraphics, p_282491_, p_281953_);
    }
}
