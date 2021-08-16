package com.vizor.test.maximizableImagePanel;

import com.vizor.test.gui.components.imagePanel.ImagePanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Paths;

public class MaximizableImagePanel extends ImagePanel {
    private static final Logger log = LoggerFactory.getLogger(MaximizableImagePanel.class);

    private static final int MAXIMIZED_WINDOW_WIDTH = 512;
    private static final int MAXIMIZED_WINDOW_HEIGHT = 512;

    public MaximizableImagePanel(String pathname) {
        super(pathname);
        setOnClickMaximize();
    }

    public MaximizableImagePanel(String pathname, int width, int height) {
        super(pathname, width, height);
        setOnClickMaximize();
    }

    private void setOnClickMaximize(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFrame frame = new JFrame(Paths.get(filename).getFileName().toString());
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                ImagePanel maximizedImage = new ImagePanel(filename);
                frame.getContentPane().add(maximizedImage);
                frame.pack();
                frame.setMinimumSize(new Dimension(MAXIMIZED_WINDOW_WIDTH, MAXIMIZED_WINDOW_HEIGHT));
                frame.setVisible(true);
            }
        });
    }

}
