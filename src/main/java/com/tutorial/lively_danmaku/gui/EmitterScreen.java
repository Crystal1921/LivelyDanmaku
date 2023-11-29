package com.tutorial.lively_danmaku.gui;

import com.tutorial.lively_danmaku.network.DanmakuNetwork;
import com.tutorial.lively_danmaku.network.EmitterPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class EmitterScreen extends AbstractContainerScreen<EmitterMenu> {
    private static final ResourceLocation DANMAKU_EMITTER = new ResourceLocation("lively_danmaku", "textures/gui/fumo_table.png");
    protected EditBox XeditBox;
    protected EditBox YeditBox;
    public EmitterScreen(EmitterMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }


    protected void init() {
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, (p_99460_) -> {
            this.onDone();
        }).bounds(this.width / 2 - 4 - 150, 210, 150, 20).build());
        this.XeditBox = new EditBox(this.font, this.width / 2 - 72, 80, 40, 10, Component.translatable("structure_block.position.y"));
        this.XeditBox.setMaxLength(15);
        this.XeditBox.setVisible(true);
        this.addWidget(this.XeditBox);
        this.YeditBox = new EditBox(this.font, this.width / 2 - 32, 80, 40, 10,Component.translatable("structure_block.position.x"));
        this.YeditBox.setMaxLength(15);
        this.YeditBox.setVisible(true);
        this.addWidget(this.YeditBox);
    }

    public void resize(@NotNull Minecraft pMinecraft, int pWidth, int pHeight) {
        String s1 = this.XeditBox.getValue();
        String s2 = this.YeditBox.getValue();
        this.init(pMinecraft, pWidth, pHeight);
        this.XeditBox.setValue(s1);
        this.YeditBox.setValue(s2);
    }

    private void onDone() {
        if (this.minecraft != null) {
            EmitterPacket emitterPacket = new EmitterPacket(parseIntegrity(this.XeditBox.getValue()),parseIntegrity(this.YeditBox.getValue()),this.getMenu().getDanmakuEmitterTE().getBlockPos());
            DanmakuNetwork.CHANNEL_POINT.sendToServer(emitterPacket);
            this.minecraft.setScreen(null);
        }
    }

    private float parseIntegrity(String pIntegrity) {
        try {
            return Float.valueOf(pIntegrity);
        } catch (NumberFormatException numberformatexception) {
            return 1.0F;
        }
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        pGuiGraphics.blit(DANMAKU_EMITTER, i , j, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(guiGraphics, pMouseX, pMouseY);
        this.XeditBox.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
        this.YeditBox.render(guiGraphics,pMouseX,pMouseY,pPartialTick);
    }
}
