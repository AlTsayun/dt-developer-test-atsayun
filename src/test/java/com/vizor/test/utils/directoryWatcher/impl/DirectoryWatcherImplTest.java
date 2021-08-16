package com.vizor.test.utils.directoryWatcher.impl;

import com.vizor.test.utils.directoryWatcher.DirectoryWatcherListener;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

class DirectoryWatcherImplTest {

    @Test
    void watchDirectory() throws IOException, InterruptedException {
        DirectoryWatcherImpl watcher = new DirectoryWatcherImpl();
        watcher.watchDirectory(Paths.get("/home/okko/workspace/dt-developer-test-atsayun/assets"), new DirectoryWatcherListener() {
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