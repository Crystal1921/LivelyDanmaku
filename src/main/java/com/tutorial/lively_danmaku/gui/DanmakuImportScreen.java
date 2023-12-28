package com.tutorial.lively_danmaku.gui;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class DanmakuImportScreen extends AbstractContainerScreen<DanmakuImportMenu> {
    private final int PADDING = 6;
    public int listWidth = 125;
    private static Logger logger = LogManager.getLogger(DanmakuImportScreen.class);
    private ImageEntry selected = null;
    private List<ImageInfo> images;
    private ImageListWidget imageListWidget;
    private ImageWidget imageWidget;
    private static final Path path = Paths.get("danmaku_image");
    private static final ResourceLocation DANMAKU_EMITTER = new ResourceLocation("lively_danmaku", "textures/gui/fumo_table.png");
    public DanmakuImportScreen(DanmakuImportMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.images = Collections.unmodifiableList(getImages());
    }

    @Override
    public void init()
    {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
        int y = this.height - 20 - PADDING;
        int fullButtonHeight = PADDING + 20 + PADDING;
        this.imageListWidget = new ImageListWidget(this, listWidth, fullButtonHeight, y - PADDING - getFontRenderer().lineHeight - PADDING);
        this.imageListWidget.setLeftPos(6);
        this.addRenderableWidget(imageListWidget);
        this.imageWidget = new ImageWidget(i,j,this);
        this.addRenderableWidget(imageWidget);
        updateCache();
    }

    @Override
    public void tick() {
        super.tick();
        imageListWidget.setSelected(selected);
    }

    private void updateCache()
    {
        if (selected == null) return;
        ImageInfo info = selected.getImageInfo();
        Path imagePath = info.path;
        TextureManager tm = this.minecraft.getTextureManager();

        try (InputStream inputStream = Files.newInputStream(imagePath)) {
            NativeImage image = NativeImage.read(inputStream);
            ResourceLocation resourceLocation = tm.register("custom_image", new DynamicTexture(image) {
                @Override
                public void upload() {
                    this.bind();
                    NativeImage imageData = this.getPixels();
                    this.getPixels().upload(0, 0, 0, 0, 0, imageData.getWidth(), imageData.getHeight(), false, false, false, false);
                }
            });
            this.imageWidget.setResourceLocation(resourceLocation);
        } catch (IOException e) {
            // 处理读取图片时的异常
            logger.error(e);
        }
    }

    public void setSelected(ImageEntry entry)
    {
        this.selected = entry == this.selected ? null : entry;
        updateCache();
    }

    public <T extends ObjectSelectionList.Entry<T>> void buildImageList(Consumer<T> modListViewConsumer, Function<ImageInfo, T> newEntry)
    {
        images.forEach(image->modListViewConsumer.accept(newEntry.apply(image)));
    }

    @NotNull
    public Minecraft getMinecraftInstance()
    {
        return minecraft;
    }

    public Font getFontRenderer()
    {
        return font;
    }

    public ImageListWidget getImageListWidget() {
        return this.imageListWidget;
    }

    private List<ImageInfo> getImages() {
        List<ImageInfo> images = new ArrayList<>();

        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            Files.walk(path)
                    .filter(p -> p.toString().toLowerCase().endsWith(".png"))
                    .forEach(p -> {
                        try {
                            BufferedImage bufferedImage = ImageIO.read(p.toFile());
                            String name = p.getFileName().toString();
                            if (bufferedImage != null) {
                                images.add(new ImageInfo(bufferedImage,name,p));
                            }
                        } catch (IOException e) {
                            logger.error(e);
                        }
                    });
        } catch (IOException e) {
            logger.error(e);
        }

        return images;
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        pGuiGraphics.blit(DANMAKU_EMITTER, i , j, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.imageListWidget.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
        super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(guiGraphics, pMouseX, pMouseY);
    }
}
