package javaserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	ServerSocket serverSocket;
	Socket clientSocket;
	static BufferedReader in;
	static DataOutputStream out;

	private static final String OUTPUT_END_OF_HEADERS = "\r\n\r\n";

	static int DEFAULT_PORT = 5000;
	int port;
	ArrayList<String> requests = new ArrayList<String>();
	
	Server () {
		this.port = DEFAULT_PORT;
	}

	Server(int port) {
		this.port = port;
	}

	public void listenOnPort() {
		try {
			serverSocket = new ServerSocket(port);
		} catch(IOException e) {
			System.out.println("Exception caught when trying to listen on port " + port + " or listening for a connection");
			System.out.println(e.getMessage());
		}
	}

	public void acceptClient(ServerSocket serverSocket) {
		try {
			clientSocket = serverSocket.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BufferedReader createReaderWithInput(Socket inputSocket) {
		try {
			return new BufferedReader(new InputStreamReader(inputSocket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void readFromSocket(BufferedReader reader, Socket clientSocket) throws IOException {
		String line;
		String request = "";
		while ((line = reader.readLine()) != null) {
			if (line.length() == 0) {
				break;
			} else {
				request += (line);
			}
		}
			requests.add(request);
	}
	
	public DataOutputStream createOutputStream() {
		try {
			DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
			return out;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void respond(DataOutputStream out, String response) throws IOException {
		out.writeBytes(response + OUTPUT_END_OF_HEADERS); 
	}

	public void tearDown() throws IOException {
		out.close();
		in.close();
		clientSocket.close();
	}
}
