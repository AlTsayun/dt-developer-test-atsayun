package com.vizor.test.utils.folderWatcher;

import java.io.File;
import java.nio.file.Path;

public interface FolderWatcherListener {
    void deleted(File file);
    void modified(File file);
    void created(File file);
}
