package com.vizor.test.utils.fileSource;

import java.io.File;

public class FileUpdatedEvent {
    private final File file;

    public File getFile() {
        return file;
    }

    public FileUpdatedEvent(File file) {
        this.file = file;
    }
}
