package com.vizor.test.gui.components.pagedPanel;

import com.vizor.test.gui.components.borderPanel.BorderPanel;
import com.vizor.test.gui.components.pagedPanel.pageIndicator.PageIndicator;
import com.vizor.test.gui.components.pagedPanel.pagedContent.PagedContent;

import java.awt.*;

public class PagedPanel extends BorderPanel {

    private int START_PAGE = 1;

    private PagedContent content;

    public PagedPanel(PagedContent content, PageIndicator pageIndicator) {
        super();
        this.content = content;

        pageIndicator.setCurrentPage(START_PAGE);
        pageIndicator.setTotalPagesCount(content.getPagesCount());
        pageIndicator.setOnPageChanged(e -> content.setCurrentPageNumber(e.getNewPageNumber()));

        content.setCurrentPageNumber(START_PAGE);
        content.setOnUpdated(e -> {
            pageIndicator.setTotalPagesCount(e.getTotalPagesCount());
            pageIndicator.setCurrentPage(e.getCurrentPageNumber());
        });
        this.add(content.getComponent(), BorderLayout.CENTER);
        this.add(pageIndicator.getComponent(), BorderLayout.SOUTH);
    }
}
