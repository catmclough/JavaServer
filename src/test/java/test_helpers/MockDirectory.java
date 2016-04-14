package test_helpers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import exceptions.DirectoryNotFoundException;
import javaserver.Directory;

public class MockDirectory {
    public static File tempFolder;
    private static File pngFile;
    public static File txtFile;
    public static String txtFileContents = "default content";

    public static Directory getMock() throws IOException, DirectoryNotFoundException {
        tempFolder = new File("tempFolder");
        tempFolder.mkdir();
        pngFile = File.createTempFile("file", ".png", tempFolder);
        txtFile = File.createTempFile("file", ".txt", tempFolder);

        FileWriter writer = new FileWriter(txtFile);
        writer.write(txtFileContents);
        writer.flush();
        writer.close();

        return new Directory(tempFolder);
    }

    public static File[] getAllFiles() {
        return new File[] {pngFile, txtFile};
    }

    public static File[] getAllTextFiles() {
        return new File[] {txtFile};
    }

    public static File[] getAllImageFiles() {
        return new File[] {pngFile};
    }

    public static void deleteFiles() {
        pngFile.delete();
        txtFile.delete();
        tempFolder.delete();
    }
}
