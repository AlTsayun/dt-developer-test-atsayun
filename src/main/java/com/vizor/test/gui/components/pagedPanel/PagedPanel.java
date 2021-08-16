package com.vizor.test.gui.components.pagedPanel;

import com.vizor.test.gui.components.borderPanel.BorderPanel;
import com.vizor.test.gui.components.pageIndicator.PageIndicator;
import com.vizor.test.gui.components.pagedContent.PagedContent;
import com.vizor.test.gui.components.pagedContent.impl.ComponentProvider;

import java.awt.*;

public class PagedPanel extends BorderPanel {

    private static final int START_PAGE = 1;

    private final ComponentProvider componentProvider;
    private final PageIndicator pageIndicator;

    private PagedContent content;

    public PagedPanel(ComponentProvider componentProvider, PagedContent content, PageIndicator pageIndicator) {
        super();
        this.componentProvider = componentProvider;
        this.content = content;
        this.pageIndicator = pageIndicator;

        componentProvider.setOnUpdated(e -> content.setComponents(e.getComponents()));
        setupPageIndicator();
        setupContent();

        this.add(content.getComponent(), BorderLayout.CENTER);
        this.add(pageIndicator.getComponent(), BorderLayout.SOUTH);
    }

    private void setupContent() {
        content.setComponents(componentProvider.getComponents());
        content.setCurrentPageNumber(START_PAGE);
        content.setOnUpdated(e -> {
            pageIndicator.setTotalPagesCount(e.getTotalPagesCount());
            pageIndicator.setCurrentPage(e.getCurrentPageNumber());
        });
    }

    private void setupPageIndicator() {
        pageIndicator.setCurrentPage(START_PAGE);
        pageIndicator.setTotalPagesCount(content.getPagesCount());
        pageIndicator.setOnPageChanged(e -> content.setCurrentPageNumber(e.getNewPageNumber()));
    }
}
