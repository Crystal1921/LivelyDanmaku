package com.tutorial.crystal.GUI;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class DanmakuScreen extends AbstractContainerScreen<DanmakuMenu> {
    private static final ResourceLocation DANMAKU_TABLE = new ResourceLocation("crystal", "textures/gui/danmaku_table.png");
    private static final ResourceLocation DANMAKU_TABLE_9 = new ResourceLocation("crystal", "textures/gui/danmaku_table_9_9.png");
    public DanmakuScreen(DanmakuMenu danmakuMenu, Inventory inventory, Component component) {
        super(danmakuMenu, inventory, component);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;

        for(int k = 0; k < 3; ++k) {
            double d0 = mouseX - (double)(i - 20);
            double d1 = mouseY - (double)(j + 14 + 19 * k);
            if (this.minecraft != null && this.minecraft.player != null && d0 >= 0D && d1 >= 0.0D && d0 < 108.0D && d1 < 19.0D && this.menu.clickMenuButton(this.minecraft.player, k)) {
                if (this.minecraft.gameMode != null) {
                    this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, k);
                }
                return true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float p_97788_, int p_97789_, int p_97790_) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(DANMAKU_TABLE, i - 80 , j, 0, 0, this.imageWidth, this.imageHeight);
        guiGraphics.blit(DANMAKU_TABLE_9, i + 120, j - 4, 0, 0, this.imageWidth - 1, this.imageHeight + 10);
    }


    public void render(@NotNull GuiGraphics guiGraphics, int p_282491_, int p_281953_, float p_282182_) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, p_282491_, p_281953_, p_282182_);
        this.renderTooltip(guiGraphics, p_282491_, p_281953_);
    }
}
