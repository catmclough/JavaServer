package javaserver;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ByteArrayInputStream;

import org.junit.Test;

public class ReaderTest {
	private Reader testReader;
	private BufferedReader testBufferedReader;
	private String exampleRequestMessage = "GET /example HTTP/1.1\r\n\r\nData";

	@Test
	public void testReaderWithSimpleRequest() throws IOException {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(exampleRequestMessage.getBytes());
		testBufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		testReader = new Reader(testBufferedReader);
		assertEquals("Simple request line was not properly read", testReader.readFromSocket(), exampleRequestMessage);
	}
}

