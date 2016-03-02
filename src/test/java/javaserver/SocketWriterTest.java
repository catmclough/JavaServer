package javaserver;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import org.junit.Before;
import org.junit.Test;

public class SocketWriterTest {
	private static SocketWriter testWriter;
	private static String standardResponse = "200 A-OKAY";

	@Before
	public void setUp() {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
		testWriter = new SocketWriter(dataOutputStream);
	}

	Exception exception;
	@Test
	public void testResponds() {
		try {
			testWriter.respond(standardResponse);
		} catch (Exception e){
			exception = e;
		}
		assertEquals(exception, null);
	}
}

