package com.vizor.test.gui.components.pagedContent.impl;

import javax.swing.*;
import java.util.List;

public class ComponentProviderUpdatedEvent {
    private final List<JComponent> components;

    public ComponentProviderUpdatedEvent(List<JComponent> components) {
        this.components = components;
    }

    public List<JComponent> getComponents() {
        return components;
    }
}
