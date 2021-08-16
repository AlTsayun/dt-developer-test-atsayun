package com.vizor.test.gui.components.pagedContent;

public class PagedContentUpdatedEvent {
    private final int totalPagesCount;
    private final int currentPageNumber;

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
