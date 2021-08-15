package com.vizor.test.gui.components.imagePanel;

public class IllegalImageFileException extends RuntimeException{
    private String pathname;

    public IllegalImageFileException(String pathname) {
        this.pathname = pathname;
    }
}
