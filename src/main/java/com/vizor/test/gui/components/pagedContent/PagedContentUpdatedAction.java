package com.vizor.test.gui.components.pagedContent;

@FunctionalInterface
public interface PagedContentUpdatedAction {
    void contentUpdated(PagedContentUpdatedEvent e);
}
