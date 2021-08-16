package com.vizor.test.gui.components.imagePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

public class ImagePanel extends JPanel {

    protected final BufferedImage image;

    protected final String filename;

    protected final Consumer<Graphics> drawAction;

    public ImagePanel(String pathname) {
        filename = pathname;
        image = readImageFile(pathname);
        drawAction = (g) -> g.drawImage(
                image,
                (this.getWidth() - image.getWidth()) / 2,
                (this.getHeight() - image.getHeight()) / 2,
                this);
    }

    public ImagePanel(String pathname, int width, int height) {
        filename = pathname;
        image = readImageFile(pathname);
        drawAction = (g) -> g.drawImage(
                image,
                (this.getWidth() - width) / 2,
                (this.getHeight() - height) / 2,
                width,
                height,
                this);
    }

    protected BufferedImage readImageFile(String pathname) {
        try {
            return ImageIO.read(new File(pathname));
        } catch (IOException ex) {
            throw new IllegalImageFileException(pathname);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawAction.accept(g);
    }


}
