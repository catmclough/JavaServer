package javaserver;

import java.util.Arrays;

public class ArgHandler {
	private static String portFlag = "-P";
	private static String publicDirectoryFlag = "-D";

	public static int getPort(String[] args, int defaultPort) {
		int portFlagIndex = Arrays.asList(args).indexOf(portFlag);
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
		int publicDirectoryFlagIndex = Arrays.asList(args).indexOf(publicDirectoryFlag);
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
