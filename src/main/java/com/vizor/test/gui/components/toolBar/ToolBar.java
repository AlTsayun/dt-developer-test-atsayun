package com.vizor.test.gui.components.toolBar;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

public class ToolBar extends JPanel {

    private static final Logger log = LoggerFactory.getLogger(ToolBar.class);
    private final JTextField tfSearch;
    private final JButton btnSearch;
    private final JButton btnUpload;
    private final JFileChooser fc;
    private final UploadTool uploadTool;
    private final SearchTool searchTool;


    public ToolBar(UploadTool uploadTool, SearchTool searchTool) {
        super();

        this.uploadTool = uploadTool;
        this.searchTool = searchTool;

        this.tfSearch = new JTextField();
        tfSearch.setMaximumSize(new Dimension(256, 0));

        this.btnSearch = new JButton("Search");
        btnSearch.addActionListener(onSearchClicked());

        this.btnUpload = new JButton("Upload image");
        btnUpload.addActionListener(onUploadClicked());

        this.fc = new JFileChooser();
        fc.addChoosableFileFilter(getImageFileFilter());
        fc.setAcceptAllFileFilterUsed(false);

        this.setLayout(setupLayout());
    }

    private ActionListener onSearchClicked() {
        return l -> {
            Thread thread = new Thread(() -> searchTool.search(tfSearch.getText()));
            thread.start();
        };
    }

    private ActionListener onUploadClicked() {
        return l -> {
            if (fc.showDialog(this, "upload") == JFileChooser.APPROVE_OPTION) {
                File source = fc.getSelectedFile();
                log.info("file selected for upload: " + source.getName());
                Thread thread = new Thread(() -> {
                    try {
                        uploadTool.upload(source);
                    } catch (IOException e) {
                        log.info("cannot copy file " + source.getName());
                        JOptionPane.showMessageDialog(this,
                                "Cannot copy file " + source.getName(),
                                "Oops...",
                                JOptionPane.ERROR_MESSAGE);
                    }
                });
                thread.start();
            }
        };
    }

    private GroupLayout setupLayout() {
        GroupLayout layout = new GroupLayout(this);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(tfSearch)
                                .addComponent(btnSearch))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(btnUpload))
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(tfSearch)
                                .addComponent(btnSearch)
                                .addComponent(btnUpload))
        );
        return layout;
    }

    private FileFilter getImageFileFilter(){
        return new FileFilter() {
            public final static String JPEG = "jpeg";
            public final static String JPG = "jpg";
            public final static String GIF = "gif";
            public final static String TIFF = "tiff";
            public final static String TIF = "tif";
            public final static String PNG = "png";

            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }

                String extension = getExtension(f);
                if (extension != null) {
                    return extension.equals(TIFF) ||
                            extension.equals(TIF) ||
                            extension.equals(GIF) ||
                            extension.equals(JPEG) ||
                            extension.equals(JPG) ||
                            extension.equals(PNG);
                }
                return false;
            }

            @Override
            public String getDescription() {
                return "Image Only";
            }

            String getExtension(File f) {
                String ext = null;
                String s = f.getName();
                int i = s.lastIndexOf('.');

                if (i > 0 &&  i < s.length() - 1) {
                    ext = s.substring(i+1).toLowerCase();
                }
                return ext;
            }
        };
    }
}
