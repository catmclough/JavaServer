package javaserver;

import java.io.File;
import java.io.IOException;
import exceptions.DirectoryNotFoundException;
import factories.*;
import routers.CobSpecRouter;
import routers.Router;
import text_parsers.ArgParser;

public class App {
     protected static final int DEFAULT_PORT = 5000;
     protected static final String DEFAULT_DIRECTORY_NAME = "public/";
     private static Directory directory;
     protected static int port;
     protected static ServerCreator serverCreator = new ServerCreator();
     protected static Server server;

    public static void main(String[] args) throws IOException, DirectoryNotFoundException {
        port = ArgParser.getPortChoice(args, DEFAULT_PORT);
        directory = new Directory(getChosenFile(args));
        Router router = new CobSpecRouter(directory);
        describeServer();
        server = serverCreator.createServer(port, router);
        server.run();
    }

    private static File getChosenFile(String[] args) {
        String chosenDirectory = ArgParser.getDirectoryChoice(args, DEFAULT_DIRECTORY_NAME);
        return new File(chosenDirectory);
    }

    private static void describeServer() throws IOException {
        System.out.println("Server running on port " + port);
        System.out.println("Public Directory: " + directory.getPath());
    }
}
