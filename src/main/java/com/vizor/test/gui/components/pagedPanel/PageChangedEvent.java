package com.vizor.test.gui.components.pagedPanel;

public class PageChangedEvent {
    private final int oldPageNumber;
    private final int newPageNumber;

    public PageChangedEvent(int oldPageNumber, int newPageNumber) {
        this.oldPageNumber = oldPageNumber;
        this.newPageNumber = newPageNumber;
    }

    public int getOldPageNumber() {
        return oldPageNumber;
    }

    public int getNewPageNumber() {
        return newPageNumber;
    }
}
