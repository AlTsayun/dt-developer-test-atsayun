package com.vizor.test.utils.folderScanner;

import java.nio.file.Path;

public interface FolderWatcher {
    void watchDirectory(Path path, FolderWatcherListener listener);
    FolderWatcherState getState();
}
