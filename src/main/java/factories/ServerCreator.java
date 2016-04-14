package factories;

import java.io.IOException;
import java.net.ServerSocket;
import javaserver.Directory;
import javaserver.Server;
import routers.CobSpecRouter;

public class ServerCreator {

    public Server createServer(int port, Directory directory, CobSpecRouter router) throws IOException {
        return new Server(new ServerSocket(port), directory, router);
    }
}
