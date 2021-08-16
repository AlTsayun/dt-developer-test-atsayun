package com.vizor.test.utils.folderWatcher;

import java.io.IOException;
import java.nio.file.Path;

public interface FolderWatcher {
    void watchDirectory(Path path, FolderWatcherListener listener) throws IOException;
    FolderWatcherState getState();
    void unwatch();
}
