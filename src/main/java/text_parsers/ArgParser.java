package text_parsers;

import java.util.Arrays;

public class ArgParser {
    private static final String PORT_FLAG = "-P";
    private static final String DIRECTORY_FLAG = "-D";

    public static int getPortChoice(String[] args, int defaultPort) {
        int portFlagIndex = Arrays.asList(args).indexOf(PORT_FLAG);
        if (portFlagIndex >= 0) {
            try {
                return Integer.parseInt(args[portFlagIndex + 1]);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                return defaultPort;
            }
        } else {
            return defaultPort;
        }
    }

    public static String getDirectoryChoice(String[] args, String defaultDirectory) {
        if (ArgParser.argsHaveDirectoryChoice(args)) {
            return formatPath(getChosenDirectory(args));
        } else {
            return defaultDirectory;
        }
    }

    private static boolean argsHaveDirectoryChoice(String[] args) {
        int directoryFlagIndex = Arrays.asList(args).indexOf(DIRECTORY_FLAG);
        if (directoryFlagIndex >= 0) {
            try {
                return args[directoryFlagIndex + 1] != null;
            } catch (ArrayIndexOutOfBoundsException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    private static String getChosenDirectory(String[] args) {
        int directoryFlagIndex = Arrays.asList(args).indexOf(DIRECTORY_FLAG);
        return args[directoryFlagIndex + 1];
    }

    private static String formatPath(String directoryPath) {
       String formattedPath = directoryPath;
       if (directoryPath.startsWith("/")) {
           formattedPath = directoryPath.substring(1);
       }
       return formattedPath;
    }
}
