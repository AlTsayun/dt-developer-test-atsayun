package com.vizor.test.gui.components.pageIndicator.impl;

import com.vizor.test.gui.components.borderPanel.BorderPanel;
import com.vizor.test.gui.components.pagedPanel.PageChangedAction;
import com.vizor.test.gui.components.pagedPanel.PageChangedEvent;
import com.vizor.test.gui.components.pageIndicator.PageIndicator;

import javax.swing.*;
import java.awt.*;

public class PageIndicatorBar implements PageIndicator {

    private final JButton btnPrevious;
    private final JButton btnNext;
    private final JLabel lblPageNumber;
    private final JComponent component;

    private int currentPageNumber;
    private int totalPagesCount;

    private PageChangedAction pageChangedAction;

    public PageIndicatorBar() {
        this.component = new BorderPanel();
        this.btnPrevious = new JButton("<");
        this.btnNext = new JButton(">");
        this.lblPageNumber = new JLabel("/");
        lblPageNumber.setHorizontalAlignment(JLabel.CENTER);
        lblPageNumber.setVerticalAlignment(JLabel.CENTER);

        btnPrevious.addActionListener((l) -> changePageNumber(currentPageNumber - 1));

        btnNext.addActionListener((l) -> changePageNumber(currentPageNumber + 1));

        component.add(btnPrevious, BorderLayout.WEST);
        component.add(btnNext, BorderLayout.EAST);
        component.add(lblPageNumber, BorderLayout.CENTER);
    }

    private void changePageNumber(int newPageNumber) {
        pageChangedAction.pageChanged(new PageChangedEvent(currentPageNumber, newPageNumber));
        currentPageNumber = newPageNumber;
        drawStateUpdated();
    }

    private void drawStateUpdated() {
        lblPageNumber.setText(currentPageNumber + " / " + totalPagesCount);
        btnPrevious.setEnabled(currentPageNumber > 1);
        btnNext.setEnabled(currentPageNumber < totalPagesCount);
    }

    @Override
    public void setOnPageChanged(PageChangedAction a) {
        pageChangedAction = a;
    }

    @Override
    public void setTotalPagesCount(int count) {
        totalPagesCount = count;
        drawStateUpdated();
    }

    @Override
    public void setCurrentPage(int pageNumber) {
        currentPageNumber = pageNumber;
        drawStateUpdated();
    }

    @Override
    public JComponent getComponent() {
        return component;
    }
}
