package javaserver;

import java.io.File;
import java.util.ArrayList;

import exceptions.DirectoryNotFoundException;

public class Directory {
    public File file;

    public Directory(File file) throws DirectoryNotFoundException {
        this.file = file;
        loadFile(file);
    }

    private void loadFile(File file) throws DirectoryNotFoundException {
        if (!file.exists()) {
           throw new DirectoryNotFoundException(getName());
        }
    }

    public String getName() {
        return file.getName();
    }

    public String[] getFileNameList() {
       return file.list();
    }

    public String[] getNonImageFileNames() {
        ArrayList<String> textFileList = new ArrayList<String>();
        for (String fileName : file.list()) {
            if (!isImage(fileName)) {
                textFileList.add(fileName);
            }
        }
        String[] textFiles = new String[textFileList.size()];
        return textFileList.toArray(textFiles);
    }

    public String[] getImageFileNames() {
        ArrayList<String> imageFileList = new ArrayList<String>();
        for (String fileName : file.list()) {
            if (isImage(fileName)) {
                imageFileList.add(fileName);
            }
        }
        String[] imageFiles = new String[imageFileList.size()];
        return imageFileList.toArray(imageFiles);
    }

    private boolean isImage(String file) {
        return file.endsWith(".gif") || file.endsWith(".jpeg") || file.endsWith(".png");
    }

    public String getPath() {
        return file.getPath() + "/";
    }
}
