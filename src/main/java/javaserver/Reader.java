package javaserver;

import java.io.BufferedReader;
import java.io.IOException;

public class Reader {
	private BufferedReader readingMechanism;

	Reader(BufferedReader readingMechanism) {
		this.readingMechanism = readingMechanism;
	}

	public String readFromSocket() {
		String line;
		String fullRequest = "";
		try {
			line = readLine();
			while (line != null) {
				if (line.length() == 0) {
					break;
				} else {
					fullRequest += line;
					line = readLine();
				}
			}
		} catch (IOException e) {
			System.out.println("Exception Caught!");
		}
		System.out.println(fullRequest);
		return fullRequest;
	}

	private String readLine() throws IOException {
		return readingMechanism.readLine();
	}
}

