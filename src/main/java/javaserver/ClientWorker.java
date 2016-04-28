package javaserver;

import java.io.IOException;
import java.net.Socket;
import http_messages.Request;
import http_messages.Response;
import io.RequestReader;
import io.SocketWriter;
import responders.Responder;

public class ClientWorker implements Runnable {
    private Socket clientSocket;
    private Router router;
    protected RequestReader reader;
    protected SocketWriter writer;
    protected RequestLog requestLog;

    public ClientWorker(Socket clientSocket, Router router) {
        this.clientSocket = clientSocket;
        this.router = router;
        this.reader = new RequestReader();
        this.writer = new SocketWriter();
        this.requestLog = RequestLog.getInstance();
    }

    public void run() {
        reader.openReader(clientSocket);
        String rawRequest = getRawRequest(reader);
        requestLog.addRequest(rawRequest);
        Request request = new Request.RequestBuilder(rawRequest).build();
        Responder responder = router.getResponder(request);
        Response response = responder.getResponse(request);

        writer.openWriter(clientSocket);
        writer.respond(response.formatResponse());
        try {
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Unable to close client socket ");
            e.printStackTrace();
        }
    }

    private String getRawRequest(RequestReader reader) {
        String request = "";
        try {
            request = reader.readFromSocket();
        } catch (IOException | NullPointerException e) {
            System.err.println("Did not get any request from socket.");
        }
        return request;
    }
}
