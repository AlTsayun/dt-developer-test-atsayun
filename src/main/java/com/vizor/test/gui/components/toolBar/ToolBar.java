package com.vizor.test.gui.components.toolBar;

import javax.swing.*;
import java.awt.*;

public class ToolBar extends JPanel {
    public ToolBar() {
        super();
        this.setBorder(BorderFactory.createLineBorder(Color.RED));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        JTextField tfSearch = new JTextField();
        tfSearch.setMaximumSize(new Dimension(256, 0));
        JButton btnSearch = new JButton("Search");
        JButton btnUpload = new JButton("Upload image");

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

    }
}
