package javaserver;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ByteArrayInputStream;

import org.junit.Before;
import org.junit.Test;

public class ReaderTest {
	private Reader testReader;
	private BufferedReader testBufferedReader;
	private String exampleRequest = "GET /example HTTP/1.1";

	@Before
	public void setUp() {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(exampleRequest.getBytes());

		testBufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		testReader = new Reader(testBufferedReader);
	}

	@Test
	public void testReadFromSocket() throws IOException {
		assertEquals(testReader.readFromSocket(), exampleRequest);
	}
}
