package com.vizor.test.gui.components.pagedContent.impl;

import javax.swing.*;
import java.util.List;

public interface ComponentProvider {
    List<JComponent> getComponents();
    void setOnUpdated(ComponentProviderUpdatedAction a);
}
