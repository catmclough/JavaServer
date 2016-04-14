package io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileReader {

    public byte[] readBytes(File file) {
        byte[] fileContents = new byte[0];

        try {
            fileContents = Files.readAllBytes(file.toPath());
            return fileContents;
        } catch (IOException e1) {
            System.err.println("FileReader was unable to read from file.");
        }
        return fileContents;
    }
}
