package com.vizor.test.gui.components.pagedPanel.pagedContent;

public class PagedContentUpdatedEvent {
    private int totalPagesCount;
    private int currentPageNumber;

    public PagedContentUpdatedEvent(int totalPagesCount, int currentPageNumber) {
        this.totalPagesCount = totalPagesCount;
        this.currentPageNumber = currentPageNumber;
    }

    public int getTotalPagesCount() {
        return totalPagesCount;
    }

    public int getCurrentPageNumber() {
        return currentPageNumber;
    }
}
