package javaserver;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class ReaderTest {
	private Reader testReader;
	private BufferedReader mockedBufferedReader;
	
	private String requestLineOne = "POST /test HTTP/1.1\r\n";
	private String requestLineTwo = "Host: www.example.com\r\n";
	private String emptyLine = "";
	
	@Before
	public void setUp() {
		mockedBufferedReader = mock(BufferedReader.class);
		testReader = new Reader(mockedBufferedReader);
	}
	
	@Test
	public void testReadFromSocket() throws IOException {
		when(mockedBufferedReader.readLine()).thenReturn(requestLineOne, requestLineTwo, emptyLine);
		assertEquals(testReader.readFromSocket(), "POST /test HTTP/1.1\r\nHost: www.example.com\r\n");
	}

	@Test
	public void testClosesReader() throws IOException {
		testReader.close();
		verify(mockedBufferedReader).close();
	}
}
