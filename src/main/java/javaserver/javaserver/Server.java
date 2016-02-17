package javaserver;
import java.net.*;
import java.io.*;

public class Server {
	static int port = 5000;
	
//	private static final String OUTPUT = "<html><head><title>Example</title></head><body><p>Worked!!!</p></body></html>";
	private static final String OUTPUT_HEADERS = "HTTP/1.1 200 OK\r\n";
	private static final String OUTPUT_END_OF_HEADERS = "\r\n\r\n";

    public static void main(String[] args) {
        try (
            ServerSocket serverSocket = new ServerSocket(port);
            Socket clientSocket = serverSocket.accept();
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
        	
        	String line;
            while ((line = in.readLine()) != null) {
              if (line.length() == 0)
                break;
              System.out.print(line + "\r\n");
              out.writeBytes(OUTPUT_HEADERS + OUTPUT_END_OF_HEADERS);;
            }

            // Close socket, breaking the connection to the client, and
            // closing the input and output streams
            out.close(); // Flush and close the output stream
            in.close(); // Close the input stream
            clientSocket.close(); // Close the socket itself
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + port + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}

