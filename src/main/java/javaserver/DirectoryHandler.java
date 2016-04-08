package javaserver;

import java.io.File;

public class DirectoryHandler {
	private static File publicDirectory;

	public static void createPublicDirectory(String directory) throws DirectoryNotFoundException {
	    String path = formatPath(directory);
	    if (directoryExists(path)) {
	        publicDirectory = new File(path);
        } else {
            System.out.println(directory + " does not exist.");
            throw new DirectoryNotFoundException(directory);
        }
	}
	
	public static File getPublicDirectory() {
	    if (publicDirectory == null) {
            try {
                createPublicDirectory(App.DEFAULT_PUBLIC_DIRECTORY);
            } catch (DirectoryNotFoundException e) {
                e.printStackTrace();
            }
	    }
	    return publicDirectory;
	}

	public static String getPublicDirectoryPath() {
	    return getPublicDirectory().getPath() + "/";
	}
	
	private static boolean directoryExists(String directoryPath) {
		File directory = new File(formatPath(directoryPath));
		return directory.exists();
	}
	
	private static String formatPath(String directoryPath) {
	   String formattedPath = directoryPath;
	   if (directoryPath.startsWith("/"))  
	       formattedPath = directoryPath.substring(1);
	   return formattedPath;
	}
}
