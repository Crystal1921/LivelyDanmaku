package com.tutorial.lively_danmaku.gui.widget;

import com.tutorial.lively_danmaku.gui.screen.DanmakuTraceScreen;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ParticleEntry extends ObjectSelectionList.Entry<ParticleEntry> {
    private final ResourceKey<ParticleType<?>> particleType;
    private final DanmakuTraceScreen screen;
    public ParticleEntry(ResourceKey<ParticleType<?>> particleType, DanmakuTraceScreen screen) {
        this.particleType = particleType;
        this.screen = screen;
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton)
    {
        screen.setSelected(this);
        screen.getParticleListWidget().setSelected(this);
        return false;
    }

    public ResourceKey<ParticleType<?>> getParticleType() {
        return particleType;
    }

    @Override
    public @NotNull Component getNarration() {
        return Component.translatable("narrator.select");
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pIndex, int pTop, int pLeft, int pWidth, int pHeight, int pMouseX, int pMouseY, boolean p_93531_, float pPartialTick) {
        Font font = this.screen.getFontRenderer();
        Component name = Component.literal(stripControlCodes(this.particleType.location().toString()));
        pGuiGraphics.drawString(font, Language.getInstance().getVisualOrder(FormattedText.composite(font.substrByWidth(name,this.screen.listWidth))), pLeft + 3, pTop + 2, 0xFFFFFF, false);
    }

    private static String stripControlCodes(String value) {
        return net.minecraft.util.StringUtil.stripColor(value);
    }
}
