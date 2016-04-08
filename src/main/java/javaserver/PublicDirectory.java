package javaserver;

import java.io.File;

public class PublicDirectory {

	private File directory;

	public PublicDirectory(File directory) {
		this.directory = directory;
	}

	public String[] getDirectoryListing() {
		return directory.list();
	}

	public String getPath() {
		return directory.getPath();
	}

	public String getDirectoryName() {
	    return directory.getName();
	}
}
