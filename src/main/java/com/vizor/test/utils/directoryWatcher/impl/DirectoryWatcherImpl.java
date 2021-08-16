package com.vizor.test.utils.directoryWatcher.impl;

import com.vizor.test.utils.directoryWatcher.DirectoryWatcher;
import com.vizor.test.utils.directoryWatcher.DirectoryWatcherListener;
import com.vizor.test.utils.directoryWatcher.DirectoryWatcherState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

public class DirectoryWatcherImpl implements DirectoryWatcher {

    private static final Logger log = LoggerFactory.getLogger(DirectoryWatcherImpl.class);

    private WatchService watcher;
    private DirectoryWatcherListener listener;
    private Path dir;
    private DirectoryWatcherState state;

    public DirectoryWatcherImpl() {
        this.state = DirectoryWatcherState.IDLE;
    }

    @Override
    public void watchDirectory(Path dir, DirectoryWatcherListener listener) throws IOException {
        this.dir = dir;
        this.watcher = setupWatcher(dir);
        this.listener = listener;

        Thread thread = new Thread(() -> {
            state = DirectoryWatcherState.ACTIVE;
            log.info("FolderWatcherImpl started watching");
            while (state == DirectoryWatcherState.ACTIVE) {

                WatchKey key;
                try {
                    key = watcher.take();
                } catch (InterruptedException x) {
                    //todo: handle directory watcher interrupted
                    return;
                }
                for (WatchEvent<?> event : key.pollEvents()) {
                    dispatchWatchEvent(event);
                }
                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            }
            state = DirectoryWatcherState.IDLE;
        });
        thread.start();
    }

    private void dispatchWatchEvent(WatchEvent<?> event) {
        WatchEvent.Kind<?> kind = event.kind();

        if (kind == OVERFLOW) {
            log.info("FolderWatcherImpl overflow");
            return;
        }

        WatchEvent<Path> ev = (WatchEvent<Path>) event;
        Path filename = Paths.get(dir.toString(), ev.context().toString());

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

    private WatchService setupWatcher(Path dir) throws IOException {
        WatchService watcher = FileSystems.getDefault().newWatchService();
        dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        log.info("FolderWatcherImpl registered to dir " + dir);
        return watcher;
    }

    @Override
    public DirectoryWatcherState getState() {
        return state;
    }

    @Override
    public void unwatch() {
        state = DirectoryWatcherState.IDLE;
        log.info("FolderWatcherImpl unwatched dir" + dir);
    }
}
