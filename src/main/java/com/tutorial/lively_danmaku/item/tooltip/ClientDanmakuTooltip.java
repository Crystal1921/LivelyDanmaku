package com.tutorial.lively_danmaku.item.tooltip;

import com.tutorial.lively_danmaku.util.ColorPoint;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ClientDanmakuTooltip implements ClientTooltipComponent {

    private final ArrayList<ColorPoint> danmakuList;

    public ClientDanmakuTooltip(RecordDanmakuTooltip recordDanmakuTooltip) {
        this.danmakuList = recordDanmakuTooltip.pointArrayList();
    }

    @Override
    public int getHeight() {
        return 120;
    }

    @Override
    public int getWidth(@NotNull Font font) {
        return 120;
    }

    @Override
    public void renderImage(@NotNull Font font, int pX, int pY, @NotNull GuiGraphics pGuiGraphics) {
        danmakuList.forEach(point -> pGuiGraphics.fill((int) (-point.x * 5 + pX), (int) (-point.y * 5 + pY), (int) (-point.x * 5 + 2 + pX), (int) (-point.y * 5 + 2 + pY), point.color));
    }
}
