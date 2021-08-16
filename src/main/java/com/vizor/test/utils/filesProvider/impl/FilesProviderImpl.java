package com.vizor.test.utils.filesProvider.impl;

import com.vizor.test.utils.fileSource.FileSource;
import com.vizor.test.utils.fileSource.FileUpdatedAction;
import com.vizor.test.utils.fileSource.FileUpdatedEvent;
import com.vizor.test.utils.filesProvider.FilesProvider;
import com.vizor.test.utils.filesProvider.FilesUpdatedAction;
import com.vizor.test.utils.filesProvider.FilesUpdatedEvent;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FilesProviderImpl implements FilesProvider {
    private List<File> files;

    private FilesUpdatedAction filesUpdatedAction;

    public FilesProviderImpl(FileSource source) throws IOException {
        this.files = source.getInitialFiles();
        source.setOnUpdated(new FileUpdatedAction() {
            @Override
            public void fileCreated(FileUpdatedEvent e) {
                files.add(e.getFile());
                filesUpdatedAction.updated(new FilesUpdatedEvent(files));
            }

            @Override
            public void fileModified(FileUpdatedEvent e) {
                files.replaceAll(f -> isEqualsByName(e.getFile(), f) ? e.getFile() : f);
                filesUpdatedAction.updated(new FilesUpdatedEvent(files));
            }

            @Override
            public void fileDeleted(FileUpdatedEvent e) {
                files.removeIf(f -> isEqualsByName(e.getFile(), f));
                filesUpdatedAction.updated(new FilesUpdatedEvent(files));
            }
        });
    }

    private boolean isEqualsByName(File f1, File f2) {
        return f1.getName().equals(f2.getName());
    }

    @Override
    public List<File> get() {
        return files;
    }

    @Override
    public void setOnFilesUpdated(FilesUpdatedAction a){
        this.filesUpdatedAction = a;
    }
}