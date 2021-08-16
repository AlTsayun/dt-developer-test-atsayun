package com.vizor.test.utils.fileSource.impl;

import com.vizor.test.utils.fileSource.FileSource;
import com.vizor.test.utils.fileSource.FileUpdatedAction;
import com.vizor.test.utils.fileSource.FileUpdatedEvent;
import com.vizor.test.utils.folderWatcher.FolderWatcher;
import com.vizor.test.utils.folderWatcher.FolderWatcherListener;
import com.vizor.test.utils.folderWatcher.impl.FolderWatcherImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FolderWatcherFileSource implements FileSource {
    private Path dir;
    private FolderWatcher folderWatcher;
    private FileUpdatedAction fileUpdatedAction;

    public FolderWatcherFileSource(Path dir, FolderWatcher folderWatcher) throws IOException {
        this.dir = dir;
        this.folderWatcher = folderWatcher;
        folderWatcher.watchDirectory(dir, new FolderWatcherListener() {
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
        return Arrays.asList(files);
    }

    @Override
    public void setOnUpdated(FileUpdatedAction a) {
        fileUpdatedAction = a;
    }
}
