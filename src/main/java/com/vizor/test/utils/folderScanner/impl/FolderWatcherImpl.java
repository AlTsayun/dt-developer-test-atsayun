package com.vizor.test.utils.folderScanner.impl;

import com.vizor.test.utils.folderScanner.FolderWatcher;
import com.vizor.test.utils.folderScanner.FolderWatcherListener;
import com.vizor.test.utils.folderScanner.FolderWatcherState;

import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

public class FolderWatcherImpl implements FolderWatcher {

    private WatchService watcher;
    private Path dir;
    private FolderWatcherState state;

    public FolderWatcherImpl() {
        this.state = FolderWatcherState.IDLE;
    }

    @Override
    public void watchDirectory(Path dir, FolderWatcherListener listener) {
        this.dir = dir;
        try {
            watcher = FileSystems.getDefault().newWatchService();
            dir.register(watcher,
                    ENTRY_CREATE,
                    ENTRY_DELETE,
                    ENTRY_MODIFY);
        } catch (IOException e) {
            System.err.println(e);
        }
        for(;;){

            // wait for key to be signaled
            WatchKey key;
            try {
                key = watcher.take();
            } catch (InterruptedException x) {
                return;
            }

            for (WatchEvent<?> event: key.pollEvents()) {
                WatchEvent.Kind kind = event.kind();

                if (kind == OVERFLOW) {
                    continue;
                }

                //The filename is the context of the event.
                WatchEvent<Path> ev = (WatchEvent<Path>)event;
                Path filename = ev.context();

                //Verify that the new file is a text file.
                try {
                    Path child = dir.resolve(filename);
                    if (!Files.probeContentType(child).equals("image")) {
                        System.err.format("New file '%s' is not a plain text file.%n", filename);
                        continue;
                    }
                } catch (IOException x) {
                    System.err.println(x);
                    continue;
                }

                //Email the file to the specified email alias.
                System.out.format("Emailing file %s%n", filename);
                //Details left to reader....
            }

            //Reset the key -- this step is critical if you want to receive
            //further watch events. If the key is no longer valid, the directory
            //is inaccessible so exit the loop.
            boolean valid = key.reset();
            if (!valid) {
                break;
            }
        }
    }

    @Override
    public FolderWatcherState getState() {
        return null;
    }
}
