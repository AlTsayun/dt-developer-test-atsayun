package com.vizor.test;

import com.vizor.test.gui.components.borderPanel.BorderPanel;
import com.vizor.test.gui.components.pageIndicator.impl.PageIndicatorBar;
import com.vizor.test.gui.components.pagedContent.impl.PagedGrid;
import com.vizor.test.gui.components.pagedPanel.PagedPanel;
import com.vizor.test.gui.components.toolBar.ToolBar;
import com.vizor.test.maximizableImagePanel.MaximizableImagePanel;
import com.vizor.test.utils.ComponentProviderWrapper.ComponentProviderWrapper;
import com.vizor.test.utils.fileSource.impl.FolderWatcherFileSource;
import com.vizor.test.utils.ComponentProviderWrapper.impl.ComponentProviderWrapperImpl;
import com.vizor.test.utils.filesProvider.impl.FilesProviderImpl;
import com.vizor.test.utils.directoryWatcher.impl.DirectoryWatcherImpl;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.function.Predicate;

public class Main {
    private static final int WINDOW_WIDTH = 1024;
    private static final int WINDOW_HEIGHT = 768;
    private static final int TILE_WIDTH = 256;
    private static final int TILE_HEIGHT = 256;
    private static final String FOLDER_PATH = "./assets";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Main()::run);
    }

    private static Predicate<File> getSearchPredicate(String query) {
        return (f) -> getImageFilePredicate().test(f) && f.getName().toLowerCase().contains(query.toLowerCase());
    }

    private static void uploadFile(File file) throws IOException {
        File dest = new File(FOLDER_PATH + "/" + file.getName());
        FileUtils.copyFile(file, dest);
    }

    private static Predicate<File> getImageFilePredicate() {
        return f -> {
            try {
                return Files.probeContentType(f.toPath()).matches("image.*");
            } catch (IOException e) {
                return false;
            }
        };
    }

    public void run() {
        JFrame frame = new JFrame("DT Developer Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        try {
            ComponentProviderWrapper componentProviderWrapper = getFilesComponentProvider();
            JComponent borderPanel = getMainPanel(componentProviderWrapper);
            frame.add(borderPanel);
        } catch (IOException e) {
            //todo: handle cannot watch folder
        }
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private JComponent getMainPanel(ComponentProviderWrapper componentProviderWrapper) {
        PagedPanel pagedPanel = new PagedPanel(
                componentProviderWrapper.getComponentProvider(),
                new PagedGrid(new ArrayList<>(), TILE_WIDTH, TILE_HEIGHT),
                new PageIndicatorBar());
        JComponent borderPanel = new BorderPanel();
        borderPanel.add(pagedPanel, BorderLayout.CENTER);
        borderPanel.add(new ToolBar(Main::uploadFile, query -> componentProviderWrapper.setPredicate(getSearchPredicate(query))),
                BorderLayout.NORTH);
        return borderPanel;
    }

    private ComponentProviderWrapperImpl getFilesComponentProvider() throws IOException {
        ComponentProviderWrapperImpl filesComponentProvider = new ComponentProviderWrapperImpl(
                new FilesProviderImpl(new FolderWatcherFileSource(Paths.get(FOLDER_PATH), new DirectoryWatcherImpl())),
                (f) -> {
                    try {
                        return new MaximizableImagePanel(f.getCanonicalPath(), TILE_WIDTH, TILE_HEIGHT);
                    } catch (IOException e) {
                        // todo: handle IOException while reading image file
                        throw new RuntimeException();
                    }
                });
        filesComponentProvider.setPredicate(getImageFilePredicate());
        return filesComponentProvider;
    }
}
