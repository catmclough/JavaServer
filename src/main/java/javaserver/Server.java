package javaserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import routers.CobSpecRouter;

public class Server {
    protected ServerSocket serverSocket;
    private Directory directory;
    private CobSpecRouter router;
    protected ExecutorService threadPool;
    protected boolean isOn = true;
    protected ClientWorker clientWorker;

    public Server(ServerSocket serverSocket, Directory directory, CobSpecRouter router) {
        this.serverSocket = serverSocket;
        this.directory = directory;
        this.router = router;
        this.threadPool = Executors.newFixedThreadPool(8);
    }

    public void run() throws IOException {
        while (isOn()) {
            Socket clientSocket = serverSocket.accept();
            clientWorker = new ClientWorker(clientSocket, router, directory);
            threadPool.execute(clientWorker);
        }
        shutDown();
    }

    public boolean isOn() {
        return isOn;
    }

    public void shutDown() throws IOException {
        threadPool.shutdown();
        serverSocket.close();
        this.isOn = false;
    }
}
