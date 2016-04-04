package javaserver;

import java.util.Arrays;

public class ArgHandler {
	private static final String PORT_FLAG = "-P";
	private static final String DIRECTORY_FLAG = "-D";

	public static int getPort(String[] args, int defaultPort) {
		int portFlagIndex = Arrays.asList(args).indexOf(PORT_FLAG);
		if (portFlagIndex >= 0)
			try {
				return Integer.parseInt(args[portFlagIndex + 1]);
			} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
				return defaultPort;
			}
		else {
			return defaultPort;
		}
	}

	public static String getChosenDirectoryName(String[] args) {
		int publicDirectoryFlagIndex = Arrays.asList(args).indexOf(DIRECTORY_FLAG);
		if (publicDirectoryFlagIndex >= 0) {
			try {
				return args[publicDirectoryFlagIndex + 1];
			} catch (ArrayIndexOutOfBoundsException e) {
				return null;
			}
		} else {
			return null;
		}
	}
}
