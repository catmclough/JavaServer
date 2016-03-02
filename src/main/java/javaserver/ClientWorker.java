package javaserver;

import java.io.IOException;
import java.net.Socket;

public class ClientWorker implements Runnable {
	private Socket client;
	private Reader reader;
	private SocketWriter writer;

	public final String TWO_HUNDRED = "HTTP/1.1 200 OK";
	public final String FOUR_OH_FOUR = "HTTP/1.1 404 Not Found";
	private String getRequest = "GET";
	private String root = "/";
	private String request;
	private String response;

	public ClientWorker(Socket clientSocket) {
		this.client = clientSocket;
	}

	public void run() {
		try {
			setReader();
			setWriter();
		  } catch (IOException e) {
			  System.out.println("Setup of reader or writer failed.");
			  e.printStackTrace();
		  }

		try {
			readAndRespond(this.reader, this.writer);
		} catch(IOException e) {
			System.out.println("Error caught when trying to read or respond in ClientWorker");
		}
	}

	public void setReader() throws IOException {
		this.reader = ServerFactory.createReader(client);
	}

	public void setWriter() throws IOException {
		this.writer = ServerFactory.createSocketWriter(client);
	}

  public void readAndRespond(Reader reader, SocketWriter writer) throws IOException {
		this.request = reader.readFromSocket();
		String[] requestParts = this.request.split(" ");
		String requestType = requestParts[0];
		String destination = requestParts[1];
		respond(requestType, destination, writer);
	}

	  public String respond(String requestType, String destination, SocketWriter writer) {
		  if (requestType.equals(getRequest)) {
			  if (destination.equals(root)) {
				  this.response = TWO_HUNDRED;
			  } else {
				  this.response = FOUR_OH_FOUR;
			  }
		  } else {
  			this.response = FOUR_OH_FOUR;
		  }

		  try {
			  writer.respond(response);
		  } catch (IOException e) {
			  System.out.println("Error caught whilst trying to respond to Client");
		  }
		  return response;
	  }
}

