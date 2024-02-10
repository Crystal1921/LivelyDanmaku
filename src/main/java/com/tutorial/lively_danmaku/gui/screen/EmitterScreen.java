package com.tutorial.lively_danmaku.gui.screen;

import com.tutorial.lively_danmaku.blockEntity.DanmakuEmitterTE;
import com.tutorial.lively_danmaku.gui.menu.EmitterMenu;
import com.tutorial.lively_danmaku.network.DanmakuNetwork;
import com.tutorial.lively_danmaku.network.EmitterPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

import static com.tutorial.lively_danmaku.block.DanmakuEmitter.RENDER;

public class EmitterScreen extends AbstractContainerScreen<EmitterMenu> {
    private static final ResourceLocation DANMAKU_EMITTER = new ResourceLocation("lively_danmaku", "textures/gui/fumo_table.png");
    protected EditBox XeditBox;
    protected EditBox YeditBox;
    protected EditBox FreqBox;
    protected EditBox deltaX;
    protected EditBox deltaY;
    protected EditBox deltaZ;
    protected CycleButton<Boolean> isRenderButton;
    private final DanmakuEmitterTE danmakuEmitterTE;
    private boolean isRender;
    public EmitterScreen(EmitterMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.danmakuEmitterTE = pMenu.getDanmakuEmitterTE();
    }

    @Override
    protected void init() {
        super.init();
        this.isRender = this.danmakuEmitterTE.getBlockState().getValue(RENDER);
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, (button) ->
                this.onDone()).bounds(this.width / 2 - 154, 210, 150, 20).build());
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, (p_99457_) ->
                this.onCancel()).bounds(this.width / 2 + 4, 210, 150, 20).build());
        this.isRenderButton = this.addRenderableWidget(CycleButton.onOffBuilder(isRender).create(this.width / 2 - 80,90,40,20,Component.translatable("block.danmaku_emitter.isRender"),
                ((pCycleButton, pValue) -> this.isRender = pValue)));
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
        this.FreqBox = new EditBox(this.font, this.width / 2 - 12, 80, 40, 10, Component.translatable("block.danmaku_emitter.freq"));
        this.FreqBox.setMaxLength(15);
        this.FreqBox.setVisible(true);
        this.FreqBox.setValue(Integer.toString(this.danmakuEmitterTE.freq));
        this.addWidget(this.FreqBox);
        this.deltaX = new EditBox(this.font, this.width / 2 - 32, 105, 30, 10, Component.translatable("block.danmaku_emitter.deltaX"));
        this.deltaX.setMaxLength(15);
        this.deltaX.setVisible(true);
        this.deltaX.setValue(Double.toString(this.danmakuEmitterTE.deltaX));
        this.addWidget(this.deltaX);
        this.deltaY = new EditBox(this.font, this.width / 2 + 3, 105, 30, 10, Component.translatable("block.danmaku_emitter.deltaY"));
        this.deltaY.setMaxLength(15);
        this.deltaY.setVisible(true);
        this.deltaY.setValue(Double.toString(this.danmakuEmitterTE.deltaY));
        this.addWidget(this.deltaY);
        this.deltaZ = new EditBox(this.font, this.width / 2 + 38, 105, 30, 10, Component.translatable("block.danmaku_emitter.deltaZ"));
        this.deltaZ.setMaxLength(15);
        this.deltaZ.setVisible(true);
        this.deltaZ.setValue(Double.toString(this.danmakuEmitterTE.deltaZ));
        this.addWidget(this.deltaZ);
    }

    @Override
    public void resize(@NotNull Minecraft pMinecraft, int pWidth, int pHeight) {
        String s1 = this.XeditBox.getValue();
        String s2 = this.YeditBox.getValue();
        String s3 = this.FreqBox.getValue();
        String s4 = this.deltaX.getValue();
        String s5 = this.deltaY.getValue();
        String s6 = this.deltaZ.getValue();
        this.init(pMinecraft, pWidth, pHeight);
        this.XeditBox.setValue(s1);
        this.YeditBox.setValue(s2);
        if (parseFloat(s3) <= 5) {
            this.FreqBox.setValue("5");
        } else{
        this.FreqBox.setValue(s3);
        }
        this.deltaX.setValue(s4);
        this.deltaY.setValue(s5);
        this.deltaZ.setValue(s6);
    }

    private void onDone() {
        if (this.minecraft != null) {
            float XRot = parseFloat(this.XeditBox.getValue());
            float YRot = parseFloat(this.YeditBox.getValue());
            int freq = (int) parseFloat(this.FreqBox.getValue());
            float deltaX = parseFloat(this.deltaX.getValue());
            float deltaY = parseFloat(this.deltaY.getValue());
            float deltaZ = parseFloat(this.deltaZ.getValue());
            if (freq <= 5) freq = 5;
            EmitterPacket emitterPacket = new EmitterPacket(XRot,YRot,freq,this.danmakuEmitterTE.getBlockPos(),deltaX,deltaY,deltaZ,isRender);
            DanmakuNetwork.CHANNEL.sendToServer(emitterPacket);
            this.minecraft.setScreen(null);
        }
    }

    private void onCancel() {
        this.minecraft.setScreen(null);
    }

    public static float parseFloat(String pIntegrity) {
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
        guiGraphics.drawString(this.font,Component.translatable("block.danmaku_emitter.freq"),this.width / 2 - 12, 70, 10526880);
        guiGraphics.drawString(this.font,Component.translatable("block.danmaku_emitter.deltaX"),this.width / 2 - 32, 95, 10526880);
        guiGraphics.drawString(this.font,Component.translatable("block.danmaku_emitter.deltaY"),this.width / 2 + 3, 95, 10526880);
        guiGraphics.drawString(this.font,Component.translatable("block.danmaku_emitter.deltaZ"),this.width / 2 + 38, 95, 10526880);
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
        this.deltaX.render(guiGraphics,pMouseX,pMouseY,pPartialTick);
        this.deltaY.render(guiGraphics,pMouseX,pMouseY,pPartialTick);
        this.deltaZ.render(guiGraphics,pMouseX,pMouseY,pPartialTick);
    }
}
