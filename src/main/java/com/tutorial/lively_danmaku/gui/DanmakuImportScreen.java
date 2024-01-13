package com.tutorial.lively_danmaku.gui;

import com.mojang.blaze3d.platform.NativeImage;
import com.tutorial.lively_danmaku.gui.widget.ImageEntry;
import com.tutorial.lively_danmaku.gui.widget.ImageInfo;
import com.tutorial.lively_danmaku.gui.widget.ImageListWidget;
import com.tutorial.lively_danmaku.gui.widget.ImageWidget;
import com.tutorial.lively_danmaku.network.DanmakuNetwork;
import com.tutorial.lively_danmaku.network.PointListPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.fml.loading.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
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
import java.util.stream.Stream;

public class DanmakuImportScreen extends AbstractContainerScreen<DanmakuImportMenu> {
    public int listWidth = 125;
    private int gridNum = 20;
    private int red = 0;
    private int green = 0;
    private int blue = 0;
    private final Logger logger = LogManager.getLogger(DanmakuImportScreen.class);
    private final List<ImageInfo> unsortedImages;
    private List<ImageInfo> images;
    private String lastFilterText = "";
    private ImageEntry selected = null;
    private ImageListWidget imageListWidget;
    private ImageWidget imageWidget;
    private EditBox search;
    private EditBox redEditBox;
    private EditBox greenEditBox;
    private EditBox blueEditBox;
    private static final Path path = Paths.get("danmaku_image");
    private static final ResourceLocation DANMAKU_EMITTER = new ResourceLocation("lively_danmaku", "textures/gui/fumo_table.png");
    public DanmakuImportScreen(DanmakuImportMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.images = Collections.unmodifiableList(getImages());
        this.unsortedImages = this.images;
    }

    @Override
    public void init()
    {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
        int PADDING = 6;
        int y = this.height - 20 - PADDING;
        int fullButtonHeight = PADDING + 20 + PADDING;
        this.imageListWidget = new ImageListWidget(this, listWidth, fullButtonHeight, y - PADDING - getFontRenderer().lineHeight - PADDING);
        this.imageListWidget.setLeftPos(6);
        this.addRenderableWidget(imageListWidget);
        this.imageWidget = new ImageWidget(i,j,this);
        this.addRenderableWidget(imageWidget);
        this.addRenderableWidget(Button.builder(Component.translatable("ui.danmaku_import.import"), (button) ->
                this.importImage()).bounds(this.width / 2 - 75, 90, 40, 20).build());
        this.search = new EditBox(getFontRenderer(), PADDING, 15, listWidth, 15, Component.translatable("fml.menu.mods.search"));
        this.search.setFocused(false);
        this.addRenderableWidget(search);
        this.redEditBox = new EditBox(getFontRenderer(), this.width / 2 - 25, this.height / 2 - 30, 30, 10, Component.translatable("block.danmaku_import.red"));
        this.redEditBox.setVisible(true);
        this.addRenderableWidget(redEditBox);
        this.greenEditBox = new EditBox(getFontRenderer(), this.width / 2 + 5, this.height / 2 - 30, 30, 10, Component.translatable("block.danmaku_import.green"));
        this.greenEditBox.setVisible(true);
        this.addRenderableWidget(greenEditBox);
        this.blueEditBox = new EditBox(getFontRenderer(), this.width / 2 + 35, this.height / 2 - 30, 30, 10, Component.translatable("block.danmaku_import.green"));
        this.blueEditBox.setVisible(true);
        this.addRenderableWidget(blueEditBox);
        updateCache();
    }

    @Override
    public void tick() {
        super.tick();
        search.tick();
        imageListWidget.setSelected(selected);
        if (!search.getValue().equals(lastFilterText))
        {
            reloadImage();
            imageListWidget.refreshList();
        }
    }

    private void importImage() {
        if (this.minecraft != null && this.minecraft.player != null && this.minecraft.gameMode != null && this.selected != null) {
            ArrayList<Point> pointList = pointList();
            DanmakuNetwork.CHANNEL.sendToServer(new PointListPacket(mergePoint(pointList)));
            if (this.menu.clickMenuButton(this.minecraft.player, 0)) {
                this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, 0);
            }
        }
    }

    private void reloadImage () {
        this.images = unsortedImages.stream()
                .filter(imageInfo -> StringUtils.toLowerCase(imageInfo.name).contains(StringUtils.toLowerCase(search.getValue())))
                .toList();
        lastFilterText = search.getValue();
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

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (this.search.keyPressed(pKeyCode, pScanCode, pModifiers)) {
            return true;
        } else {
            return this.search.isFocused() && this.search.isVisible() && pKeyCode != 256 || super.keyPressed(pKeyCode, pScanCode, pModifiers);
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

    private ArrayList<Point> pointList() {
        int width = this.selected.getImageInfo().width;
        int height = this.selected.getImageInfo().height;
        BufferedImage image = this.selected.getImageInfo().bufferedImage;
        ArrayList<Point> pointList = new ArrayList<>();
        for (int i = 0; i < width; i += width / gridNum) {
            for (int j = 0; j < height; j += height / gridNum) {
                if (isColor(image.getRGB(i,j))) {
                    pointList.add(new Point(i,j));
                }
            }
        }
        return pointList;
    }

    public static ArrayList<Long> mergePoint (ArrayList<Point> pointArrayList) {
        ArrayList<Long> merged = new ArrayList<>();
        for (Point point : pointArrayList) {
            merged.add(merge(point.x,point.y));
        }
        return merged;
    }

    public static ArrayList<Point> extractPoint (ArrayList<Long> longs) {
        ArrayList<Point> pointArrayList = new ArrayList<>();
        for (Long value : longs) {
            pointArrayList.add(extract(value));
        }
        return pointArrayList;
    }

    private List<ImageInfo> getImages() {
        List<ImageInfo> images = new ArrayList<>();

        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            try (Stream<Path> paths = Files.walk(path)) {
                paths.filter(p -> p.toString().toLowerCase().endsWith(".png"))
                        .forEach(p -> {
                            try {
                                BufferedImage bufferedImage = ImageIO.read(p.toFile());
                                String name = p.getFileName().toString();
                                if (bufferedImage != null) {
                                    images.add(new ImageInfo(bufferedImage, name, p));
                                }
                            } catch (IOException e) {
                                logger.error(e);
                            }
                        });
            }
        } catch (IOException e) {
            logger.error(e);
        }

        return images;
    }

    private boolean isColor(int rgb) {
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;

        return red == this.red && green == this.green && blue == this.blue;
    }

    public static long merge(int value1, int value2) {
        value1 &= 0b1111111111; // 确保 value1 在 0 到 1023 之间
        value2 &= 0b1111111111; // 确保 value2 在 0 到 1023 之间

        long result = 0L;
        result |= (long) value1 << 10;
        result |= value2;
        return result;
    }

    public static Point extract(long value) {
        int y = (int) (value & 0b1111111111); // 获取低 10 位作为 y 值
        int x = (int) ((value >> 10) & 0b1111111111); // 获取高 10 位作为 x 值
        return new Point(x, y);
    }

    public void renderText (@NotNull GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        guiGraphics.drawString(getFontRenderer(),Component.translatable("block.danmaku_import.rgb"),this.width / 2 + 35, this.height / 2 - 40, Color.WHITE.getRGB());
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
        this.search.render(guiGraphics, pMouseX , pMouseY, pPartialTick);
        this.redEditBox.render(guiGraphics, pMouseX , pMouseY, pPartialTick);
        this.greenEditBox.render(guiGraphics, pMouseX , pMouseY, pPartialTick);
        this.blueEditBox.render(guiGraphics, pMouseX , pMouseY, pPartialTick);
        this.renderTooltip(guiGraphics, pMouseX, pMouseY);
        this.renderText(guiGraphics,pMouseX,pMouseY,pPartialTick);
        super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
    }
}
