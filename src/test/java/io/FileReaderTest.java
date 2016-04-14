package io;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import exceptions.DirectoryNotFoundException;
import javaserver.Directory;
import test_helpers.MockDirectory;

public class FileReaderTest {
    private static FileReader fileReader = new FileReader();

    @BeforeClass
    public static void setup() throws IOException, DirectoryNotFoundException {
        @SuppressWarnings("unused") //.getMock() creates static text file
        Directory mockDir = MockDirectory.getMock();
    }
    
    @AfterClass
    public static void deleteMockDirectoryFiles() {
        MockDirectory.deleteFiles();
    }

    @Test
    public void testFileReader() {
        File file = MockDirectory.txtFile;
        assertTrue(Arrays.equals(MockDirectory.txtFileContents.getBytes(), fileReader.readBytes(file)));
    }
}
