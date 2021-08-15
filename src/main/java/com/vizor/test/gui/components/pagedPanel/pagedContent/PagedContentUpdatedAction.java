package com.vizor.test.gui.components.pagedPanel.pagedContent;

@FunctionalInterface
public interface PagedContentUpdatedAction {
    void contentUpdated(PagedContentUpdatedEvent e);
}
