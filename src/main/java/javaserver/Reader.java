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
			while ((line = readLine()) != null) {
				if (line.length() == 0) {
					break;
				} else {
					fullRequest += line;
				}
			}
		} catch (IOException e) {
      //This is caught every time the reader doesn't see a request, which is called in        a while loop!
		}
		return fullRequest;
	}

	private String readLine() throws IOException {
		return readingMechanism.readLine();
	}
}
