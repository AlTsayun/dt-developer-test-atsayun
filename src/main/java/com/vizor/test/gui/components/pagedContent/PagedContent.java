package com.vizor.test.gui.components.pagedContent;

import javax.swing.*;
import java.util.List;

public interface PagedContent {
    JComponent getComponent();
    void setCurrentPageNumber(int pageNumber);
    int getPagesCount();
    void setOnUpdated(PagedContentUpdatedAction a);

    void setComponents(List<JComponent> components);
}
