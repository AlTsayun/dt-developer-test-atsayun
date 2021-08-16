package com.vizor.test.utils.filesProvider;

@FunctionalInterface
public interface FilesUpdatedAction {
    void updated(FilesUpdatedEvent e);
}
