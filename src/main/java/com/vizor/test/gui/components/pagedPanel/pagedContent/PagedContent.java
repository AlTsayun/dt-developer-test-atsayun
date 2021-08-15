package com.vizor.test.gui.components.pagedPanel.pagedContent;

import javax.swing.*;

public interface PagedContent {
    JComponent getComponent();
    void setCurrentPageNumber(int pageNumber);
    int getPagesCount();
    void setOnUpdated(PagedContentUpdatedAction a);
}
