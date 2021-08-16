package com.vizor.test.utils.directoryWatcher;

import java.io.IOException;
import java.nio.file.Path;

public interface DirectoryWatcher {
    void watchDirectory(Path path, DirectoryWatcherListener listener) throws IOException;
    DirectoryWatcherState getState();
    void unwatch();
}
