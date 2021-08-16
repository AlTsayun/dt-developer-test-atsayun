package com.vizor.test;

import com.vizor.test.gui.components.borderPanel.BorderPanel;
import com.vizor.test.gui.components.pageIndicator.impl.PageIndicatorBar;
import com.vizor.test.gui.components.pagedContent.impl.PagedGrid;
import com.vizor.test.gui.components.pagedPanel.PagedPanel;
import com.vizor.test.gui.components.toolBar.ToolBar;
import com.vizor.test.gui.components.maximizableImagePanel.MaximizableImagePanel;
import com.vizor.test.utils.ComponentProviderWrapper.ComponentProviderWrapper;
import com.vizor.test.utils.fileSource.impl.FolderWatcherFileSource;
import com.vizor.test.utils.ComponentProviderWrapper.impl.ComponentProviderWrapperImpl;
import com.vizor.test.utils.filesProvider.impl.FilesProviderImpl;
import com.vizor.test.utils.directoryWatcher.impl.DirectoryWatcherImpl;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.function.Predicate;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(ToolBar.class);

    private static final int WINDOW_WIDTH = 1024;
    private static final int WINDOW_HEIGHT = 768;
    private static final int TILE_WIDTH = 256;
    private static final int TILE_HEIGHT = 256;
    private static final String DIRECTORY_PATH = "./assets";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Main()::run);
    }

    private static Predicate<File> getSearchPredicate(String query) {
        return (f) -> getImageFilePredicate().test(f) && f.getName().toLowerCase().contains(query.toLowerCase());
    }

    private static void uploadFile(File file) throws IOException {
        File dest = new File(DIRECTORY_PATH + "/" + file.getName());
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
            log.info("cannot watch directory" + DIRECTORY_PATH);
            JOptionPane.showMessageDialog(frame,
                    "Looks like something is blocking the folder from being read. Try reopen the app and make sure nothing holds \"" + DIRECTORY_PATH + "\" folder.",
                    "Oops...",
                    JOptionPane.ERROR_MESSAGE);
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
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
                new FilesProviderImpl(new FolderWatcherFileSource(Paths.get(DIRECTORY_PATH), new DirectoryWatcherImpl())),
                (f) -> new MaximizableImagePanel(f.getPath(), TILE_WIDTH, TILE_HEIGHT));
        filesComponentProvider.setPredicate(getImageFilePredicate());
        return filesComponentProvider;
    }
}
