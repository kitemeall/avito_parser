package com.example.ri_test.test_test.service.fileUploader;

public class FileNotCreatedException extends RuntimeException {

    public FileNotCreatedException(String s) {
        super(s);
    }

    public FileNotCreatedException(Exception e) {
        super(e);
    }
}
