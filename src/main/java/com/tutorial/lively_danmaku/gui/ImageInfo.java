package com.tutorial.lively_danmaku.gui;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.nio.file.Path;

public class ImageInfo {
    public BufferedImage bufferedImage;
    public String name;
    public Path path;
    public int width;
    public int height;
    public ImageInfo (BufferedImage bufferedImage, String name, Path path) {
        this.bufferedImage = bufferedImage;
        this.name = name;
        this.path = path;
        this.width = bufferedImage.getWidth();
        this.height = bufferedImage.getHeight();
    }
}
