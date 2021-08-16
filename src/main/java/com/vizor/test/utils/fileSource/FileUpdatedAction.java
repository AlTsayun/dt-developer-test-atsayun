package com.vizor.test.utils.fileSource;
public interface FileUpdatedAction {
    void fileCreated(FileUpdatedEvent e);
    void fileModified(FileUpdatedEvent e);
    void fileDeleted(FileUpdatedEvent e);
}
