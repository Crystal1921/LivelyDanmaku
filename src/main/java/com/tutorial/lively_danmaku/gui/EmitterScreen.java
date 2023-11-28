package com.tutorial.lively_danmaku.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.CommandSuggestions;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class EmitterScreen extends AbstractContainerScreen<EmitterMenu> {
    private static final ResourceLocation FUMO_TABLE = new ResourceLocation("lively_danmaku", "textures/gui/fumo_table.png");
    protected EditBox editBox;
    public EmitterScreen(EmitterMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }


    protected void init() {
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.editBox = new EditBox(this.font, this.width / 2 - 72, 80, 80, 20, Component.translatable("structure_block.position.y"));
        this.editBox.setMaxLength(15);
        this.editBox.setVisible(true);
        this.addWidget(this.editBox);
    }

    public void resize(Minecraft pMinecraft, int pWidth, int pHeight) {
        String s1 = this.editBox.getValue();
        this.editBox.setValue(s1);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        pGuiGraphics.blit(FUMO_TABLE, i , j, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(guiGraphics, pMouseX, pMouseY);
        this.editBox.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
    }
}
