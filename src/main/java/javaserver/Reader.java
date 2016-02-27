package javaserver;

import java.io.BufferedReader;
import java.io.IOException;

public class Reader {
	private BufferedReader readingMechanism;

	Reader(BufferedReader readingMechanism) {
		this.readingMechanism = readingMechanism; 
	}
	
	public String readFromSocket() throws IOException {
		String line;
		String fullRequest = "";
		while ((line = readLine()) != null) {
			if (line.length() == 0) {
				break;
			} else {
				fullRequest += line;
			}
		}	
		return fullRequest;
	}
	
	private String readLine() throws IOException {
		return readingMechanism.readLine();
	}
}
