package com.vizor.test.gui.components.pagedContent.impl;

import com.vizor.test.gui.components.pagedContent.PagedContent;
import com.vizor.test.gui.components.pagedContent.PagedContentUpdatedAction;
import com.vizor.test.gui.components.pagedContent.PagedContentUpdatedEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.List;

public class PagedGrid implements PagedContent {

    private List<JComponent> tiles;

    private JComponent component;

    private int tileWidth;
    private int tileHeight;

    private int rowsCount;
    private int colsCount;

    private int currentPageNumber;
    private int totalPagesCount;

    private PagedContentUpdatedAction contentUpdated;

    public PagedGrid(List<JComponent> tiles, int tileWidth, int tileHeight) {
        this.tiles = tiles;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.rowsCount = 1;
        this.colsCount = 1;
        this.component = new JPanel();
        component.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                recalculateGrid();
                drawTilesForCurrentPage();
            }

            @Override
            public void componentMoved(ComponentEvent e) { }

            @Override
            public void componentShown(ComponentEvent e) { }

            @Override
            public void componentHidden(ComponentEvent e) { }
        });
    }

    private void drawTilesForCurrentPage() {
        component.removeAll();
        component.revalidate();
        component.repaint();
        component.setLayout(new GridLayout(0, Math.max(colsCount, 1)));
        for (int i = getStartIndexForPage(currentPageNumber);
             i < getStartIndexForPage(currentPageNumber + 1) && i < tiles.size(); i++) {
            component.add(tiles.get(i));
        }
        component.revalidate();
        component.repaint();
    }

    private int getStartIndexForPage(int i) {
        return (i - 1) * colsCount * rowsCount;
    }

    private void recalculateGrid() {
        colsCount = component.getWidth() / tileWidth;
        rowsCount = component.getHeight() / tileHeight;
        currentPageNumber = 1;
        totalPagesCount = (int) Math.ceil(tiles.size() / (double) (colsCount * rowsCount));
        if (contentUpdated != null) {
            contentUpdated.contentUpdated(new PagedContentUpdatedEvent(totalPagesCount, currentPageNumber));
        }

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

    @Override
    public void setComponents(List<JComponent> components) {
        this.tiles = components;
        recalculateGrid();
        drawTilesForCurrentPage();
    }
}
