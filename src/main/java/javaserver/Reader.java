package javaserver;

import java.io.BufferedReader;
import java.io.IOException;

public class Reader {

	private BufferedReader readingMechanism;
	private char lastCharOfRequest = (char) -1;

	Reader(BufferedReader readingMechanism) {
		this.readingMechanism = readingMechanism;
	}

	public String readFromSocket() throws IOException {
		String fullRequest = "";
		fullRequest += getRequestLine();
		if (readingMechanism.ready()) {
			fullRequest += System.lineSeparator();
			fullRequest += getData();
		}
		readingMechanism.close();
		return fullRequest;
	}

	private String getRequestLine() throws IOException {
		return readingMechanism.readLine();
	}

	private String getData() throws IOException {
		String data = "";
		while (readingMechanism.ready()) {
			char nextChar = read();
			if (nextChar != lastCharOfRequest) {
				data += nextChar;
			} else {
				break;
			}
		}
		return data;
	}

	private char read() throws IOException {
		return (char) readingMechanism.read();
	}
}
