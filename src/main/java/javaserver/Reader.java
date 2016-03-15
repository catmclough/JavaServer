package javaserver;

import java.io.BufferedReader;
import java.io.IOException;

public class Reader {
	private BufferedReader readingMechanism;
	private int minRequestLength = 1;
	private char lastCharOfRequest = (char) -1;

	Reader(BufferedReader readingMechanism) {
		this.readingMechanism = readingMechanism;
	}

	public String readFromSocket() throws IOException {
		char nextChar;
		String fullRequest = "";
		while (readingMechanism.ready() || fullRequest.length() < minRequestLength) {
			nextChar = read();
			if (nextChar != lastCharOfRequest) {
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

