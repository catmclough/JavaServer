package javaserver;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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
	
	@Test
	public void testResponds() throws IOException {
		testWriter.respond(standardResponse);
//		assertEquals()
//		verify(mockDataOutputStream).writeBytes(mockResponse);
	}

//	class MockDataOutputStream extends DataOutputStream {
//		MockDataOutputStream(OutputStream outputStream) throws IOException {
//			super(outputStream);
//		}
//	
//		@Override
//		public void writeBytes(String toWrite) throws IOException {
//			return toWrite;
//		}
//	}
	
}
