package com.kabh.utils;

import java.io.File;
import java.io.InputStream;

public class FileUtils {

    /**
     * Method loads the files from the resouces and returns as inputStream.
     * @param fileName
     * @return
     */
    public static InputStream getFileFromResourceAsStream(String fileName) {

        // The class loader that loaded the class
        ClassLoader classLoader = new FileUtils().getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }
}
