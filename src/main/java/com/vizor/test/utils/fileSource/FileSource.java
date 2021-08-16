package com.vizor.test.utils.fileSource;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileSource {
    List<File> getInitialFiles() throws IOException;
    void setOnUpdated(FileUpdatedAction a);
}
