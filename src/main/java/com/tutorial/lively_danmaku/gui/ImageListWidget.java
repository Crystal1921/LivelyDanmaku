package com.tutorial.lively_danmaku.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;

public class ImageListWidget extends ObjectSelectionList<ImageEntry> {
    private final int listWidth;
    private DanmakuImportScreen parent;
    public ImageListWidget(DanmakuImportScreen parent, int listWidth, int top, int bottom) {
        super(parent.getMinecraftInstance(), listWidth, parent.height, top, bottom, parent.getFontRenderer().lineHeight * 2 + 8);
        this.parent = parent;
        this.listWidth = listWidth;
        this.refreshList();
    }

    public void refreshList() {
        this.clearEntries();
        parent.buildImageList(this::addEntry, mod -> new ImageEntry(mod, this.parent));
    }

    @Override
    protected void renderBackground(GuiGraphics guiGraphics)
    {
        this.parent.renderBackground(guiGraphics);
    }

    @Override
    protected int getScrollbarPosition()
    {
        return this.listWidth;
    }

    @Override
    public int getRowWidth()
    {
        return this.listWidth;
    }

}
