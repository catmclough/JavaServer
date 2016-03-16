package javaserver;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class ResponderTest {
	Responder testResponder;
	SocketWriter writer;

	String twoHundred = HTTPStatusCodes.TWO_HUNDRED;
	
	@Before
	public void setUp() {
		App.configureRoutes();
		testResponder = new Responder();
    	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
		this.writer = new SocketWriter(dataOutputStream);
	}
	
	@Test
	public void testResponds() throws IOException {
		Request request = new Request("GET", "/");

		assertNull(writer.latestResponse);
		testResponder.respond(request, new ServerFactory(), writer);
		assertNotNull(writer.latestResponse);
	}
}

