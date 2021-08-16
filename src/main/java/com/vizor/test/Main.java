package com.vizor.test;

import com.vizor.test.gui.components.borderPanel.BorderPanel;
import com.vizor.test.gui.components.pagedPanel.PagedPanel;
import com.vizor.test.gui.components.pagedPanel.pageIndicator.impl.PageIndicatorBar;
import com.vizor.test.gui.components.pagedPanel.pagedContent.impl.ComponentProvider;
import com.vizor.test.gui.components.pagedPanel.pagedContent.impl.ComponentProviderUpdatedAction;
import com.vizor.test.gui.components.pagedPanel.pagedContent.impl.PagedGrid;
import com.vizor.test.gui.components.toolBar.ToolBar;
import com.vizor.test.maximizableImagePanel.MaximizableImagePanel;
import com.vizor.test.utils.fileSource.impl.FolderWatcherFileSource;
import com.vizor.test.utils.filesProvider.FilesProvider;
import com.vizor.test.utils.folderWatcher.impl.FolderWatcherImpl;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Main()::run);
    }

    public void run() {
        JFrame frame = new JFrame("DT Developer Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        try {
            FilesProvider filesProvider = new FilesProvider(new FolderWatcherFileSource(Paths.get("./assets"), new FolderWatcherImpl()));

            List<JComponent> tiles = new ArrayList<>();
            filesProvider.get(f -> {
                        try {
                            return Files.probeContentType(f.toPath()).matches("image.*");
                        } catch (IOException e) {
                            return false;
                        }
                    })
                    .forEach(f -> {
                        try {
                            tiles.add(new MaximizableImagePanel(f.getCanonicalPath(), 256, 256));
                        } catch (IOException e) {
                            //cannot get canonical path of a file
                        }
                    });
            PagedPanel pagedPanel = new PagedPanel(new PagedGrid(new ComponentProvider() {
                @Override
                public List<JComponent> getComponents() {
                    return tiles;
                }

                @Override
                public void setOnUpdated(ComponentProviderUpdatedAction a) {

                }
            }, 256, 256), new PageIndicatorBar());
            JComponent borderPanel = new BorderPanel();
            borderPanel.add(pagedPanel, BorderLayout.CENTER);
            borderPanel.add(new ToolBar(), BorderLayout.NORTH);

            frame.add(borderPanel);
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
        } catch (IOException e) {
            //cannot watch folder
        }
    }
}
