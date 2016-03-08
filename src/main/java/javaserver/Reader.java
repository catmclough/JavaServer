package javaserver;

import java.io.BufferedReader;
import java.io.IOException;

public class Reader {
	private BufferedReader readingMechanism;

	Reader(BufferedReader readingMechanism) {
		this.readingMechanism = readingMechanism;
	}

	public String readFromSocket() {
		char nextChar;
		String fullRequest = "";
		try {
			while (readingMechanism.ready() || fullRequest.length() < 5) {
				nextChar = read();
				if (nextChar != -1) {
					fullRequest += nextChar;
				} else {
					break;
				}
			}
		} catch (IOException e) {
			System.out.println("Exception Caught!");
		}
		return fullRequest;
	}

	private char read() throws IOException {
		return (char) readingMechanism.read();
	}
}

