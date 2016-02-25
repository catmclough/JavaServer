package javaserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Reader {
	private BufferedReader readingMechanism;
	
	public void initialize(Socket clientSocket) throws IOException {
		this.readingMechanism = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}
	
	public String readLine() throws IOException {
		return readingMechanism.readLine();
	}
	
	public void close() throws IOException {
		readingMechanism.close();
	}
}
