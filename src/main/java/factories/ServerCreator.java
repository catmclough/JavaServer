package factories;

import java.io.IOException;
import java.net.ServerSocket;
import javaserver.Server;
import routers.Router;

public class ServerCreator {

    public Server createServer(int port, Router router) throws IOException {
        return new Server(new ServerSocket(port), router);
    }
}