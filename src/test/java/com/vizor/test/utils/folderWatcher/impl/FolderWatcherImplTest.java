package com.vizor.test.utils.folderWatcher.impl;

import com.vizor.test.utils.fileSource.FileSource;
import com.vizor.test.utils.folderWatcher.FolderWatcherListener;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

class FolderWatcherImplTest {

    @Test
    void watchDirectory() throws IOException, InterruptedException {
        FolderWatcherImpl watcher = new FolderWatcherImpl();
        watcher.watchDirectory(Paths.get("/home/okko/workspace/dt-developer-test-atsayun/assets"), new FolderWatcherListener() {
            @Override
            public void deleted(File file) {
                System.out.println("deleted " + file.getAbsolutePath());
            }

            @Override
            public void modified(File file) {
                System.out.println("modified " + file.getAbsolutePath());
            }

            @Override
            public void created(File file) {
                System.out.println("created " + file.getAbsolutePath());
            }
        });
        Thread.sleep(30_000);
        watcher.unwatch();
    }
}