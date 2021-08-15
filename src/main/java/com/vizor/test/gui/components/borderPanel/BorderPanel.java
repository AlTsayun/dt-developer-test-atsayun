package com.vizor.test.gui.components.borderPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class BorderPanel extends JPanel {

    public BorderPanel() {
        super();
        this.setLayout(new BorderLayout());
        Random rand = new Random();
        int randomNum = rand.nextInt();
        this.setBorder(BorderFactory.createLineBorder(Color.decode(String.valueOf(randomNum))));
    }

}