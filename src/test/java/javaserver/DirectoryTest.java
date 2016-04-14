package javaserver;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import exceptions.DirectoryNotFoundException;
import test_helpers.MockDirectory;

public class DirectoryTest {
    private static Directory directory;

    @BeforeClass
    public static void setup() throws IOException, DirectoryNotFoundException {
        directory = MockDirectory.getMock();
    }

    @AfterClass
    public static void deleteTempFolder() {
        MockDirectory.deleteFiles();
    }

    @Test
    public void testInvalidDirectoryThrowsDirectoryNotFound() throws IOException {
        boolean errorCaught = false;
        try {
            File nonExistantFile = new File("thisFileDoesNotExist");
            directory = new Directory(nonExistantFile);
        } catch (DirectoryNotFoundException e) {
            errorCaught = true;
        }
        assertTrue(errorCaught);
    }

    @Test
    public void testGetsFileName() throws DirectoryNotFoundException {
        assertEquals(directory.getName(), MockDirectory.tempFolder.getName());
    }

    @Test
    public void testGetsDirectoryContentsAsList() throws DirectoryNotFoundException {
        for (File file : MockDirectory.getAllFiles()) {
            assertTrue(Arrays.asList(directory.getFileNameList()).contains(file.getName()));
        }
    }

    @Test
    public void testGetsNonImageFilesOnly() throws DirectoryNotFoundException {
        for (File nonImageFile : MockDirectory.getAllTextFiles()) {
           assertTrue(Arrays.asList(directory.getNonImageFileNames()).contains(nonImageFile.getName()));
        }

        for (File imageFile : MockDirectory.getAllImageFiles()) {
           assertFalse(Arrays.asList(directory.getNonImageFileNames()).contains(imageFile.getName()));
        }
    }

    @Test
    public void testGetsImageFilesOnly() throws DirectoryNotFoundException {
        for (File imageFile : MockDirectory.getAllImageFiles()) {
           assertTrue(Arrays.asList(directory.getImageFileNames()).contains(imageFile.getName()));
        }

        for (File nonImageFile : MockDirectory.getAllTextFiles()) {
           assertFalse(Arrays.asList(directory.getImageFileNames()).contains(nonImageFile.getName()));
        }
    }
}
