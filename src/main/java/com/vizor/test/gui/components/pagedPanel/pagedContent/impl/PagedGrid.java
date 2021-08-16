package com.vizor.test.gui.components.pagedPanel.pagedContent.impl;

import com.vizor.test.gui.components.pagedPanel.pagedContent.PagedContent;
import com.vizor.test.gui.components.pagedPanel.pagedContent.PagedContentUpdatedAction;
import com.vizor.test.gui.components.pagedPanel.pagedContent.PagedContentUpdatedEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.List;

public class PagedGrid implements PagedContent {

    private ComponentProvider componentProvider;
    private List<JComponent> tiles;

    private JComponent component;

    private int tileWidth;
    private int tileHeight;

    private int rowsCount;
    private int colsCount;

    private int currentPageNumber;
    private int totalPagesCount;

    private PagedContentUpdatedAction contentUpdated;

    public PagedGrid(ComponentProvider componentProvider, int tileWidth, int tileHeight) {
        this.componentProvider = componentProvider;
        componentProvider.setOnUpdated((e) -> tiles = e.getComponents());
        this.tiles = componentProvider.getComponents();
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.rowsCount = 1;
        this.colsCount = 1;
        this.component = new JPanel();
        component.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                recalculateGrid();
                if (contentUpdated != null) {
                    contentUpdated.contentUpdated(new PagedContentUpdatedEvent(totalPagesCount, currentPageNumber));
                }
                drawTilesForCurrentPage();
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
    }

    private void drawTilesForCurrentPage() {
        component.removeAll();
        component.revalidate();
        component.repaint();
        component.setLayout(new GridLayout(0, colsCount));
        for (int i = (currentPageNumber - 1) * colsCount * rowsCount; i < currentPageNumber * colsCount * rowsCount && i < tiles.size(); i++) {
            component.add(tiles.get(i));
        }
    }

    private void recalculateGrid() {
        colsCount = component.getWidth() / tileWidth;
        rowsCount = component.getHeight() / tileHeight;
        currentPageNumber = 1;
        totalPagesCount = (int) Math.ceil(tiles.size() / (double) (colsCount * rowsCount));
    }

    public void setComponentProvider(ComponentProvider componentProvider) {
        this.componentProvider = componentProvider;
        componentProvider.setOnUpdated((e) -> tiles = e.getComponents());
        recalculateGrid();
    }

    public void setTileWidth(int tileWidth) {
        this.tileWidth = tileWidth;
        recalculateGrid();
    }

    public void setTileHeight(int tileHeight) {
        this.tileHeight = tileHeight;
        recalculateGrid();
    }


    @Override
    public JComponent getComponent() {
        return component;
    }

    @Override
    public void setCurrentPageNumber(int pageNumber) {
        currentPageNumber = pageNumber;
        drawTilesForCurrentPage();
    }

    @Override
    public int getPagesCount() {
        return totalPagesCount;
    }

    @Override
    public void setOnUpdated(PagedContentUpdatedAction a) {
        contentUpdated = a;
    }
}
