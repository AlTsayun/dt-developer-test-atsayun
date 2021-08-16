package com.vizor.test.gui.components.pageIndicator;

import com.vizor.test.gui.components.pagedPanel.PageChangedAction;

import javax.swing.*;

public interface PageIndicator {
    void setOnPageChanged(PageChangedAction a);
    void setTotalPagesCount(int count);
    void setCurrentPage(int pageNumber);
    JComponent getComponent();
}
