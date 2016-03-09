package javaserver;

import java.io.BufferedReader;
import java.io.IOException;

public class Reader {
	private BufferedReader readingMechanism;

	Reader(BufferedReader readingMechanism) {
		this.readingMechanism = readingMechanism;
	}

	public String readFromSocket() throws IOException {
		char nextChar;
		String fullRequest = "";
		while (readingMechanism.ready() || fullRequest.length() < 5) {
			nextChar = read();
			if (nextChar != -1) {
				fullRequest += nextChar;
			} else {
				break;
			}
		}
		return fullRequest;
	}

	private char read() throws IOException {
		return (char) readingMechanism.read();
	}
}

