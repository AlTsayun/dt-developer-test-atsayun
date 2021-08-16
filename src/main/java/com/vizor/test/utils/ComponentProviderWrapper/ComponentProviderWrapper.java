package com.vizor.test.utils.ComponentProviderWrapper;

import com.vizor.test.gui.components.pagedContent.impl.ComponentProvider;

import java.io.File;
import java.util.function.Predicate;

public interface ComponentProviderWrapper {
    void setPredicate(Predicate<File> predicate);
    ComponentProvider getComponentProvider();
}
