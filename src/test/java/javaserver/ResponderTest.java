package javaserver;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class ResponderTest {
	SocketWriter writer;
	Responder responder;
	
	@Before
	public void setUp() {
		this.responder = new Responder();
    	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
		this.writer = new SocketWriter(dataOutputStream);
	}	
	
	@Test
	public void testGetResponse() throws IOException {
		HashMap<String, String> request = new HashMap<String, String>();
		request.put("Type", "GET");
		request.put("URI", "/");
		assertEquals(responder.respond(request, writer), responder.TWO_HUNDRED);
	}
	
	@Test
	public void testPostResponse() {
		HashMap<String, String> request = new HashMap<String, String>();
		request.put("Type", "POST");
		request.put("URI", "/form");
		assertEquals(responder.respond(request, writer), responder.TWO_HUNDRED);
	}
	
	@Test
	public void testPutResponse() {
		HashMap<String, String> request = new HashMap<String, String>();
		request.put("Type", "PUT");
		request.put("URI", "/form");
		assertEquals(responder.respond(request, writer), responder.TWO_HUNDRED);
	}
	
	@Test
	public void testFourOhFour() throws IOException {
		HashMap<String, String> request = new HashMap<String, String>();
		request.put("Type", "GET");
		request.put("URI", "/foo");
		assertEquals(responder.respond(request, writer), responder.FOUR_OH_FOUR);
	}
}

