package com.tutorial.lively_danmaku.gui.screen;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.platform.NativeImage;
import com.tutorial.lively_danmaku.gui.menu.DanmakuImportMenu;
import com.tutorial.lively_danmaku.gui.widget.ImageEntry;
import com.tutorial.lively_danmaku.gui.widget.ImageInfo;
import com.tutorial.lively_danmaku.gui.widget.ImageListWidget;
import com.tutorial.lively_danmaku.gui.widget.ImageWidget;
import com.tutorial.lively_danmaku.network.DanmakuNetwork;
import com.tutorial.lively_danmaku.network.PointListPacket;
import com.tutorial.lively_danmaku.util.ColorPoint;
import com.tutorial.lively_danmaku.util.ImportColorMode;
import com.tutorial.lively_danmaku.util.MathMethod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.state.properties.StructureMode;
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
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.tutorial.lively_danmaku.gui.screen.EmitterScreen.parseFloat;

public class DanmakuImportScreen extends AbstractContainerScreen<DanmakuImportMenu> {
    public int listWidth = 125;
    private int gridNum = 20;
    private int red = 0;
    private int green = 0;
    private int blue = 0;
    private ImportColorMode colorMode = ImportColorMode.SIMPLE;
    private static final ImmutableList<ImportColorMode> MODE_IMMUTABLE_LIST = ImmutableList.copyOf(ImportColorMode.values());
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
    public static final Path DANMAKU_IMAGE = Paths.get("danmaku_image");
    private static final ResourceLocation DANMAKU_EMITTER = new ResourceLocation("lively_danmaku", "textures/gui/fumo_table.png");
    public DanmakuImportScreen(DanmakuImportMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.images = Collections.unmodifiableList(getImages());
        this.unsortedImages = this.images;
    }

    @Override
    public void init()
    {
        super.init();
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
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
        this.addRenderableWidget(CycleButton.<ImportColorMode>builder((serializedName) -> Component.translatable("ui.danmaku_import." + serializedName.getSerializedName()))
                .withValues(MODE_IMMUTABLE_LIST)
                .displayOnlyValue()
                .withInitialValue(this.colorMode)
                .create(this.width / 2 - 80, this.height / 2 - 80, 50, 20, Component.literal("MODE"), (colorModeCycleButton, updatedMode) -> this.updateMode(updatedMode)));
        this.redEditBox = new EditBox(getFontRenderer(), this.width / 2 - 25, this.height / 2 - 14, 30, 10, Component.translatable("block.danmaku_import.red"));
        this.redEditBox.setVisible(true);
        this.redEditBox.setValue("0");
        this.addRenderableWidget(redEditBox);
        this.greenEditBox = new EditBox(getFontRenderer(), this.width / 2 + 5, this.height / 2 - 14, 30, 10, Component.translatable("block.danmaku_import.green"));
        this.greenEditBox.setVisible(true);
        this.greenEditBox.setValue("0");
        this.addRenderableWidget(greenEditBox);
        this.blueEditBox = new EditBox(getFontRenderer(), this.width / 2 + 35, this.height / 2 - 14, 30, 10, Component.translatable("block.danmaku_import.green"));
        this.blueEditBox.setVisible(true);
        this.blueEditBox.setValue("0");
        this.addRenderableWidget(blueEditBox);
        updateCache();
    }

    private void updateMode(ImportColorMode mode) {
        this.colorMode = mode;
        this.redEditBox.setVisible(false);
        this.greenEditBox.setVisible(false);
        this.blueEditBox.setVisible(false);
        switch (mode){
            case SIMPLE ->{
                this.redEditBox.setVisible(true);
                this.greenEditBox.setVisible(true);
                this.blueEditBox.setVisible(true);
            }
            case COLORFUL -> {

            }
        }
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
            this.red = (int) parseFloat(this.redEditBox.getValue());
            this.green = (int) parseFloat(this.greenEditBox.getValue());
            this.blue = (int) parseFloat(this.blueEditBox.getValue());
            ArrayList<ColorPoint> pointList = getPointList();
            if (pointList != null && !pointList.isEmpty()){
                this.imageWidget.pointList = pointList.stream().map(ColorPoint::getPoint).collect(Collectors.toCollection(ArrayList::new));
                DanmakuNetwork.CHANNEL.sendToServer(new PointListPacket(MathMethod.mergePoint(pointList)));
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
                    Objects.requireNonNull(this.getPixels()).upload(0, 0, 0, 0, 0, imageData.getWidth(), imageData.getHeight(), false, false, false, false);
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

    private ArrayList<ColorPoint> getPointList() {
        int width = this.selected.getImageInfo().width;
        int height = this.selected.getImageInfo().height;
        int widthNum = width / gridNum;
        int heightNum = height / gridNum;
        BufferedImage image = this.selected.getImageInfo().bufferedImage;
        ArrayList<ColorPoint> pointList = new ArrayList<>();
        if (width <= gridNum) return null;
        for (int i = 0; i < width; i += widthNum) {
            for (int j = 0; j < height; j += heightNum) {
                if (isColor(image.getRGB(i,j)) || colorMode == ImportColorMode.COLORFUL) {
                    pointList.add(new ColorPoint((double) i / widthNum * 4, (double) j / heightNum * 4,0,image.getRGB(i,j)));
                }
            }
        }
        return pointList;
    }

    private List<ImageInfo> getImages() {
        List<ImageInfo> images = new ArrayList<>();

        try {
            if (!Files.exists(DANMAKU_IMAGE)) {
                Files.createDirectories(DANMAKU_IMAGE);
            }

            try (Stream<Path> paths = Files.walk(DANMAKU_IMAGE)) {
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

    @Override
    protected void renderLabels(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {}

    public void renderText (@NotNull GuiGraphics guiGraphics) {
        if (colorMode == ImportColorMode.SIMPLE) {
            guiGraphics.drawString(getFontRenderer(),Component.translatable("block.danmaku_import.rgb"),this.width / 2 - 25, this.height / 2 - 24, Color.WHITE.getRGB());
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
        this.imageListWidget.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
        this.search.render(guiGraphics, pMouseX , pMouseY, pPartialTick);
        this.redEditBox.render(guiGraphics, pMouseX , pMouseY, pPartialTick);
        this.greenEditBox.render(guiGraphics, pMouseX , pMouseY, pPartialTick);
        this.blueEditBox.render(guiGraphics, pMouseX , pMouseY, pPartialTick);
        this.renderTooltip(guiGraphics, pMouseX, pMouseY);
        this.renderText(guiGraphics);
        super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
    }
}
