package com.tutorial.lively_danmaku.gui;

import com.tutorial.lively_danmaku.blockEntity.DanmakuEmitterTE;
import com.tutorial.lively_danmaku.network.DanmakuNetwork;
import com.tutorial.lively_danmaku.network.EmitterPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class EmitterScreen extends AbstractContainerScreen<EmitterMenu> {
    private static final ResourceLocation DANMAKU_EMITTER = new ResourceLocation("lively_danmaku", "textures/gui/fumo_table.png");
    protected EditBox XeditBox;
    protected EditBox YeditBox;
    protected EditBox FreqBox;
    private final DanmakuEmitterTE danmakuEmitterTE;
    public EmitterScreen(EmitterMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.danmakuEmitterTE = pMenu.getDanmakuEmitterTE();
    }

    @Override
    protected void init() {
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, (button) ->
                this.onDone()).bounds(this.width / 2 - 4 - 150, 210, 150, 20).build());
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, (p_99457_) ->
                this.onCancel()).bounds(this.width / 2 + 4, 210, 150, 20).build());
        this.XeditBox = new EditBox(this.font, this.width / 2 - 12, 55, 40, 10, Component.translatable("block.danmaku_emitter.pitch"));
        this.XeditBox.setMaxLength(15);
        this.XeditBox.setVisible(true);
        this.XeditBox.setValue(Float.toString(this.danmakuEmitterTE.XRot));
        this.addWidget(this.XeditBox);
        this.YeditBox = new EditBox(this.font, this.width / 2 + 32, 55, 40, 10, Component.translatable("block.danmaku_emitter.yaw"));
        this.YeditBox.setMaxLength(15);
        this.YeditBox.setVisible(true);
        this.YeditBox.setValue(Float.toString(this.danmakuEmitterTE.YRot));
        this.addWidget(this.YeditBox);
        this.FreqBox = new EditBox(this.font, this.width / 2 - 12, 75, 40, 10, Component.translatable("block.danmaku_emitter.freq"));
        this.FreqBox.setMaxLength(15);
        this.FreqBox.setVisible(true);
        this.FreqBox.setValue(Integer.toString(this.danmakuEmitterTE.freq));
        this.addWidget(this.FreqBox);
    }

    @Override
    public void resize(@NotNull Minecraft pMinecraft, int pWidth, int pHeight) {
        String s1 = this.XeditBox.getValue();
        String s2 = this.YeditBox.getValue();
        String s3 = this.FreqBox.getValue();
        this.init(pMinecraft, pWidth, pHeight);
        this.XeditBox.setValue(s1);
        this.YeditBox.setValue(s2);
        this.FreqBox.setValue(s3);
    }

    private void onDone() {
        if (this.minecraft != null) {
            float XRot = parseIntegrity(this.XeditBox.getValue());
            float YRot = parseIntegrity(this.YeditBox.getValue());
            int freq = (int)parseIntegrity(this.FreqBox.getValue());
            EmitterPacket emitterPacket = new EmitterPacket(XRot,YRot,freq,this.danmakuEmitterTE.getBlockPos());
            DanmakuNetwork.CHANNEL_POINT.sendToServer(emitterPacket);
            this.minecraft.setScreen(null);
        }
    }

    private void onCancel() {
        this.minecraft.setScreen(null);
    }

    private float parseIntegrity(String pIntegrity) {
        try {
            return Float.parseFloat(pIntegrity);
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

    private void renderText (GuiGraphics guiGraphics) {
        guiGraphics.drawString(this.font,Component.translatable("block.danmaku_emitter.yaw"),this.width / 2 - 12, 45,10526880);
        guiGraphics.drawString(this.font,Component.translatable("block.danmaku_emitter.pitch"),this.width / 2 + 32, 45, 10526880);
        guiGraphics.drawString(this.font,Component.translatable("block.danmaku_emitter.freq"),this.width / 2 - 12, 65, 10526880);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(guiGraphics, pMouseX, pMouseY);
        this.renderText(guiGraphics);
        this.XeditBox.render(guiGraphics,pMouseX,pMouseY,pPartialTick);
        this.YeditBox.render(guiGraphics,pMouseX,pMouseY,pPartialTick);
        this.FreqBox.render(guiGraphics,pMouseX,pMouseY,pPartialTick);
    }
}
