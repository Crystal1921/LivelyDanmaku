package com.tutorial.lively_danmaku.gui.screen;

import com.tutorial.lively_danmaku.gui.menu.DanmakuTraceMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class DanmakuTraceScreen extends AbstractContainerScreen<DanmakuTraceMenu> {
    public DanmakuTraceScreen(DanmakuTraceMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {

    }
}
