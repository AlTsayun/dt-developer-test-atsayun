package com.vizor.test.utils.filesProvider;

import java.io.File;
import java.util.List;

public class FilesUpdatedEvent {
    private List<File> files;

    public FilesUpdatedEvent(List<File> files) {
        this.files = files;
    }

    public List<File> getFiles() {
        return files;
    }
}
