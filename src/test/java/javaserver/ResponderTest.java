package javaserver;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class ResponderTest {
	ResponseBuilder responseBuilder;
	SocketWriter writer;
	Responder responder;

	String twoHundred = HTTPStatusCodes.TWO_HUNDRED;
	
	@Before
	public void setUp() {
		App.configureRoutes();
		this.responseBuilder = new ResponseBuilder();
		this.responder = new Responder(responseBuilder);
    	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
		this.writer = new SocketWriter(dataOutputStream);
	}
	
	@Test
	public void testResponds() throws IOException {
		HashMap<String, String> request = new HashMap<String, String>();
		request.put("Type", "GET");
		request.put("URI", "/");
		responder.respond(request, writer);
		assert(writer.latestResponse.contains(twoHundred));
	}
}

