package com.vizor.test.utils.filesProvider;

import java.io.File;
import java.util.List;

public interface FilesProvider {
    List<File> get();
    void setOnFilesUpdated(FilesUpdatedAction a);
}
