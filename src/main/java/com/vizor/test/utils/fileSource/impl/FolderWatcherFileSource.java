package com.vizor.test.utils.fileSource.impl;

import com.vizor.test.utils.fileSource.FileSource;
import com.vizor.test.utils.fileSource.FileUpdatedAction;
import com.vizor.test.utils.fileSource.FileUpdatedEvent;
import com.vizor.test.utils.directoryWatcher.DirectoryWatcher;
import com.vizor.test.utils.directoryWatcher.DirectoryWatcherListener;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FolderWatcherFileSource implements FileSource {
    private Path dir;
    private FileUpdatedAction fileUpdatedAction;

    public FolderWatcherFileSource(Path dir, DirectoryWatcher directoryWatcher) throws IOException {
        this.dir = dir;
        directoryWatcher.watchDirectory(dir, new DirectoryWatcherListener() {
            @Override
            public void deleted(File file) {
                if (fileUpdatedAction != null){
                    fileUpdatedAction.fileDeleted(new FileUpdatedEvent(file));
                }
            }

            @Override
            public void modified(File file) {
                if (fileUpdatedAction != null){
                    fileUpdatedAction.fileModified(new FileUpdatedEvent(file));
                }
            }

            @Override
            public void created(File file) {
                if (fileUpdatedAction != null){
                    fileUpdatedAction.fileCreated(new FileUpdatedEvent(file));
                }
            }
        });
    }

    @Override
    public List<File> getInitialFiles() throws IOException{
        File[] files = dir.toFile().listFiles();
        if (files == null){
            throw new IOException("cannot list files of dir " + dir);
        }
        return new ArrayList<>(Arrays.asList(files));
    }

    @Override
    public void setOnUpdated(FileUpdatedAction a) {
        fileUpdatedAction = a;
    }
}
