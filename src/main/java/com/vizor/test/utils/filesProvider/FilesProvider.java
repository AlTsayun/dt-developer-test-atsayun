package com.vizor.test.utils.filesProvider;

import com.vizor.test.utils.fileSource.FileSource;
import com.vizor.test.utils.fileSource.FileUpdatedAction;
import com.vizor.test.utils.fileSource.FileUpdatedEvent;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FilesProvider {
    private List<File> files;

    private FilesUpdatedAction filesUpdatedAction;

    public FilesProvider(FileSource source) throws IOException {
        this.files = source.getInitialFiles();
        source.setOnUpdated(new FileUpdatedAction() {
            @Override
            public void fileCreated(FileUpdatedEvent e) {
                files.add(e.getFile());
                filesUpdatedAction.updated(new FilesUpdatedEvent(files));
            }

            @Override
            public void fileModified(FileUpdatedEvent e) {
                files.add(e.getFile());
                filesUpdatedAction.updated(new FilesUpdatedEvent(files));
            }

            @Override
            public void fileDeleted(FileUpdatedEvent e) {
                files.add(e.getFile());
                filesUpdatedAction.updated(new FilesUpdatedEvent(files));
            }
        });
    }

    public List<File> get(Predicate<File> filterPredicate) {
        return files.stream()
                .filter(filterPredicate)
                .collect(Collectors.toList());
    }

    public void setOnFilesUpdated(FilesUpdatedAction a){
        this.filesUpdatedAction = a;
    }
}