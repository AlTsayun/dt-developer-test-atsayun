package com.vizor.test.utils.ComponentProviderWrapper.impl;

import com.vizor.test.gui.components.pagedContent.impl.ComponentProvider;
import com.vizor.test.gui.components.pagedContent.impl.ComponentProviderUpdatedAction;
import com.vizor.test.gui.components.pagedContent.impl.ComponentProviderUpdatedEvent;
import com.vizor.test.utils.ComponentProviderWrapper.ComponentProviderWrapper;
import com.vizor.test.utils.ComponentProviderWrapper.RefreshableComponentProvider;
import com.vizor.test.utils.filesProvider.FilesProvider;

import javax.swing.*;
import java.io.File;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ComponentProviderWrapperImpl implements ComponentProviderWrapper {

    private Predicate<File> predicate;
    private FilesProvider filesProvider;
    private Function<File, JComponent> mapFunc;
    private RefreshableComponentProvider componentProvider;

    public ComponentProviderWrapperImpl(FilesProvider filesProvider, Function<File, JComponent> mapFunc) {
        this.filesProvider = filesProvider;
        this.componentProvider = setupComponentProvider();
        this.mapFunc = mapFunc;
        this.predicate = (f) -> true;
    }

    @Override
    public void setPredicate(Predicate<File> predicate) {
        this.predicate = predicate;
        componentProvider.refresh();
    }

    @Override
    public ComponentProvider getComponentProvider() {
        return componentProvider;
    }

    private List<JComponent> convertFilesToComponents(Function<File, JComponent> mapFunc, List<File> files) {
        return files.stream()
                .filter(predicate)
                .map(mapFunc)
                .collect(Collectors.toList());
    }

    private RefreshableComponentProvider setupComponentProvider(){
        return new RefreshableComponentProvider() {
            private ComponentProviderUpdatedAction updatedAction;

            @Override
            public List<JComponent> getComponents() {
                return convertFilesToComponents(mapFunc, filesProvider.get());
            }

            @Override
            public void setOnUpdated(ComponentProviderUpdatedAction a) {
                updatedAction = a;
                filesProvider.setOnFilesUpdated(e -> a.updated(new ComponentProviderUpdatedEvent(
                        convertFilesToComponents(mapFunc, e.getFiles()))));
            }

            @Override
            public void refresh() {
                if (updatedAction != null) {
                    updatedAction.updated(new ComponentProviderUpdatedEvent(
                            convertFilesToComponents(mapFunc, filesProvider.get())));
                }
            }
        };
    }
}
