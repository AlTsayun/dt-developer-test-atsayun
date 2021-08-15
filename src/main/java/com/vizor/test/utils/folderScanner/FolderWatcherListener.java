package com.vizor.test.utils.folderScanner;

import java.io.File;

public interface FolderWatcherListener {
    void updated(File oldDirectoryState, File newDirectoryState);
}
