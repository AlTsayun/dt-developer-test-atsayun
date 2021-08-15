package com.vizor.test.gui.components.pagedPanel;

@FunctionalInterface
public interface PageChangedAction {
    void pageChanged(PageChangedEvent e);
}
