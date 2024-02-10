package com.tutorial.lively_danmaku.gui.screen;

import com.tutorial.lively_danmaku.gui.menu.DanmakuColorMenu;
import com.tutorial.lively_danmaku.gui.widget.ColorSlider;
import com.tutorial.lively_danmaku.gui.widget.ColorWidget;
import com.tutorial.lively_danmaku.network.ColorPacket;
import com.tutorial.lively_danmaku.network.DanmakuNetwork;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

import static com.tutorial.lively_danmaku.gui.screen.EmitterScreen.parseFloat;

public class DanmakuColorScreen extends AbstractContainerScreen<DanmakuColorMenu> {
    private static final ResourceLocation DANMAKU_COLOR = new ResourceLocation("lively_danmaku", "textures/gui/fumo_table.png");
    private EditBox redEditBox;
    private EditBox greenEditBox;
    private EditBox blueEditBox;
    private ColorWidget colorWidget;
    private ColorSlider redSlider;
    private ColorSlider greenSlider;
    private ColorSlider blueSlider;
    public DanmakuColorScreen(DanmakuColorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.addRenderableWidget(Button.builder(Component.translatable("ui.danmaku_import.import"), (button) ->
                this.ColorChange()).bounds(this.width / 2 - 75, 90, 40, 20).build());
        this.redEditBox = new EditBox(getFontRenderer(), i + 65, j + 10, 30, 10, Component.translatable("block.danmaku_import.red"));
        this.redEditBox.setVisible(true);
        this.redEditBox.setValue("0");
        this.addRenderableWidget(redEditBox);
        this.greenEditBox = new EditBox(getFontRenderer(), i + 65, j + 25, 30, 10, Component.translatable("block.danmaku_import.green"));
        this.greenEditBox.setVisible(true);
        this.greenEditBox.setValue("0");
        this.addRenderableWidget(greenEditBox);
        this.blueEditBox = new EditBox(getFontRenderer(), i + 65, j + 40, 30, 10, Component.translatable("block.danmaku_import.green"));
        this.blueEditBox.setVisible(true);
        this.blueEditBox.setValue("0");
        this.addRenderableWidget(blueEditBox);
        this.redSlider = this.addRenderableWidget(new ColorSlider( i + 100, j + 10, redEditBox));
        this.greenSlider = this.addRenderableWidget(new ColorSlider(i + 100, j + 25, greenEditBox));
        this.blueSlider = this.addRenderableWidget(new ColorSlider(i + 100, j + 40, blueEditBox));
        this.colorWidget = this.addRenderableWidget(new ColorWidget(i + 65, j + 60));
    }

    @Override
    public void resize(@NotNull Minecraft pMinecraft, int pWidth, int pHeight) {
        String s1 = this.redEditBox.getValue();
        String s2 = this.greenEditBox.getValue();
        String s3 = this.blueEditBox.getValue();
        this.init(pMinecraft, pWidth, pHeight);
        this.redEditBox.setValue(s1);
        this.greenEditBox.setValue(s2);
        this.blueEditBox.setValue(s3);
    }

    private void ColorChange() {
        if (this.minecraft != null && this.minecraft.player != null && this.minecraft.gameMode != null) {
            int red = (int) parseFloat(this.redEditBox.getValue());
            int green = (int) parseFloat(this.greenEditBox.getValue());
            int blue = (int) parseFloat(this.blueEditBox.getValue());
            red &= 0b11111110;
            green &= 0b11111110;
            blue &= 0b11111110;
            Color color = new Color(red,green,blue);
            this.colorWidget.setColor(red,green,blue);
            DanmakuNetwork.CHANNEL.sendToServer(new ColorPacket(color.getRGB(), menu.containerId));
            if (this.menu.clickMenuButton(this.minecraft.player, 0)) {
                this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, 0);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        int red = (int) parseFloat(this.redEditBox.getValue());
        int green = (int) parseFloat(this.greenEditBox.getValue());
        int blue = (int) parseFloat(this.blueEditBox.getValue());
        red &= 0b11111110;
        green &= 0b11111110;
        blue &= 0b11111110;
        this.colorWidget.setColor(red,green,blue);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        pGuiGraphics.blit(DANMAKU_COLOR, i , j, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(guiGraphics, pMouseX, pMouseY);
        this.redEditBox.render(guiGraphics, pMouseX , pMouseY, pPartialTick);
        this.greenEditBox.render(guiGraphics, pMouseX , pMouseY, pPartialTick);
        this.blueEditBox.render(guiGraphics, pMouseX , pMouseY, pPartialTick);
    }

    public Font getFontRenderer()
    {
        return font;
    }
}
