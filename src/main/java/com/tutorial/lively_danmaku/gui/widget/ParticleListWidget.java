package com.tutorial.lively_danmaku.gui.widget;

import com.tutorial.lively_danmaku.gui.screen.DanmakuTraceScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import org.jetbrains.annotations.NotNull;

public class ParticleListWidget extends ObjectSelectionList<ParticleEntry> {
    private final int listWidth;
    private final DanmakuTraceScreen parent;

    public ParticleListWidget(DanmakuTraceScreen parent, int listWidth, int top, int bottom) {
        super(parent.getMinecraftInstance(), listWidth, parent.height, top, bottom, parent.getFontRenderer().lineHeight * 2 + 8);
        this.parent = parent;
        this.listWidth = listWidth;
        this.refreshList();
    }

    public void refreshList() {
        this.clearEntries();
        parent.buildImageList(this::addEntry, particleType -> new ParticleEntry(particleType, this.parent));
    }
    @Override
    protected void renderBackground(@NotNull GuiGraphics guiGraphics) {}

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
