package com.vizor.test.utils.folderWatcher.impl;

import com.vizor.test.utils.folderWatcher.FolderWatcher;
import com.vizor.test.utils.folderWatcher.FolderWatcherListener;
import com.vizor.test.utils.folderWatcher.FolderWatcherState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

public class FolderWatcherImpl implements FolderWatcher {

    private static final Logger log = LoggerFactory.getLogger(FolderWatcherImpl.class);

    private WatchService watcher;
    private Path dir;
    private FolderWatcherState state;

    public FolderWatcherImpl() {
        this.state = FolderWatcherState.IDLE;
    }

    @Override
    public void watchDirectory(Path dir, FolderWatcherListener listener) throws IOException {
        this.dir = dir;
        watcher = FileSystems.getDefault().newWatchService();
        dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        log.info("FolderWatcherImpl registered to dir " + dir);

        Thread thread = new Thread(() -> {
            state = FolderWatcherState.ACTIVE;
            log.info("FolderWatcherImpl started watching");
            while (state == FolderWatcherState.ACTIVE) {

                // wait for key to be signaled
                WatchKey key;
                try {
                    key = watcher.take();
                } catch (InterruptedException x) {
                    return;
                }

                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind kind = event.kind();

                    if (kind == OVERFLOW) {
                        log.info("FolderWatcherImpl overflow");
                        continue;
                    }

                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path filename = ev.context();

                    if (kind == ENTRY_CREATE) {
                        log.info("FolderWatcherImpl created" + filename);
                        listener.created(filename.toFile());
                    } else if (kind == ENTRY_MODIFY) {
                        log.info("FolderWatcherImpl modified" + filename);
                        listener.modified(filename.toFile());
                    } else if (kind == ENTRY_DELETE) {
                        log.info("FolderWatcherImpl deleted" + filename);
                        listener.deleted(filename.toFile());
                    }
                }
                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            }
            state = FolderWatcherState.IDLE;
        });
        thread.start();
    }

    @Override
    public FolderWatcherState getState() {
        return state;
    }

    @Override
    public void unwatch() {
        state = FolderWatcherState.IDLE;
        log.info("FolderWatcherImpl unwatched dir" + dir);
    }
}
