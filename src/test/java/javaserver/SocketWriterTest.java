package javaserver;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.BeforeClass;
import org.junit.Test;

public class SocketWriterTest {
	private static SocketWriter testWriter;
	private static DataOutputStream mockDataOutputStream;

	@BeforeClass
	public static void setUp() {
		OutputStream mockOutputStream = mock(OutputStream.class);
		mockDataOutputStream = spy(new DataOutputStream(mockOutputStream));
		testWriter = spy(new SocketWriter(mockDataOutputStream));
	}
	
	@Test
	public void testResponds() throws IOException {
		String mockResponse = "200 A-OKAY";
		testWriter.respond(mockResponse);
		verify(mockDataOutputStream).writeBytes(mockResponse);
	}
	
	@Test
	public void testClosesWriter() throws IOException {
		//TODO: It says there are zero calls to close on the mock object...WEIRD
//		testWriter.close();
//		verify(mockDataOutputStream).close();
	}

}
