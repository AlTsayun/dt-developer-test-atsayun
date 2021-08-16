package com.vizor.test.utils.directoryWatcher;

import java.io.File;

public interface DirectoryWatcherListener {
    void deleted(File file);
    void modified(File file);
    void created(File file);
}
