package com.tutorial.lively_danmaku.util;

import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;

public enum ImportColorMode implements StringRepresentable {
    SIMPLE("simple"),
    COLORFUL("colorful");

    private final String name;
    private final Component displayName;
    private ImportColorMode(String pName) {
        this.name = pName;
        this.displayName = Component.translatable("ui.danmaku_import." + pName);
    }
    @Override
    public String getSerializedName() {
        return this.name;
    }
    public Component getDisplayName() {
        return this.displayName;
    }
}
