package com.tutorial.lively_danmaku.gui.widget;

import com.tutorial.lively_danmaku.gui.screen.DanmakuImportScreen;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import org.jetbrains.annotations.NotNull;

public class ImageEntry extends ObjectSelectionList.Entry<ImageEntry>{
    private final ImageInfo imageInfo;
    private final DanmakuImportScreen importScreen;
    private static String stripControlCodes(String value) { return net.minecraft.util.StringUtil.stripColor(value); }
    public ImageEntry (ImageInfo imageInfo, DanmakuImportScreen importScreen) {
        this.imageInfo = imageInfo;
        this.importScreen = importScreen;
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton)
    {
        importScreen.setSelected(this);
        importScreen.getImageListWidget().setSelected(this);
        return false;
    }

    @Override
    public @NotNull Component getNarration() {
        return Component.translatable("narrator.select");
    }

    @Override
    public void render(@NotNull GuiGraphics pGuiGraphics, int pIndex, int pTop, int pLeft, int pWidth, int pHeight, int pMouseX, int pMouseY, boolean isMouseOver, float pPartialTick) {
        Font font = this.importScreen.getFontRenderer();
        Component name = Component.literal(stripControlCodes(this.imageInfo.name));
        pGuiGraphics.drawString(font, Language.getInstance().getVisualOrder(FormattedText.composite(font.substrByWidth(name,this.importScreen.listWidth))), pLeft + 3, pTop + 2, 0xFFFFFF, false);
    }

    public ImageInfo getImageInfo() {
        return imageInfo;
    }
}
