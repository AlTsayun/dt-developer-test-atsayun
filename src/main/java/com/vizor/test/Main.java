package com.vizor.test;

import com.vizor.test.gui.components.borderPanel.BorderPanel;
import com.vizor.test.gui.components.imagePanel.ImagePanel;
import com.vizor.test.gui.components.pagedPanel.PagedPanel;
import com.vizor.test.gui.components.pagedPanel.pageIndicator.impl.PageIndicatorBar;
import com.vizor.test.gui.components.pagedPanel.pagedContent.impl.PagedGrid;
import com.vizor.test.gui.components.toolBar.ToolBar;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final int WIDTH = 512;
    private static final int HEIGHT = 512;

    public void run() {
        JFrame frame = new JFrame("DT Developer Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        List<JComponent> tiles = new ArrayList<>();
        tiles.add(new ImagePanel("/home/okko/workspace/dt-developer-test-atsayun/assets/0a84326a-258e-410d-acb0-75d143de2fce.png", 256, 256));
        for (int i = 1; i < 13; i++) {
            tiles.add(new JLabel(String.valueOf(i)));
        }
        PagedPanel pagedPanel = new PagedPanel(new PagedGrid(tiles, 256, 256), new PageIndicatorBar());
        JComponent borderPanel =  new BorderPanel();
        borderPanel.add(pagedPanel, BorderLayout.CENTER);
        borderPanel.add(new ToolBar(), BorderLayout.NORTH);

        frame.add(borderPanel);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Main()::run);
    }
}
