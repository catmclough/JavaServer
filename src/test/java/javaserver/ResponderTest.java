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
	String twoHundred;
	String fourOhFour;
	
	@Before
	public void setUp() {
		this.responder = new Responder();
    	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
		this.writer = new SocketWriter(dataOutputStream);
		
		twoHundred = responder.responseCodes.get("200");
		fourOhFour = responder.responseCodes.get("404");
	}	
	
	@Test
	public void testGetResponse() throws IOException {
		HashMap<String, String> request = new HashMap<String, String>();
		request.put("Type", "GET");
		request.put("URI", "/");
		assertEquals(responder.respond(request, writer), twoHundred);
	}
	
	@Test
	public void testPostResponse() throws IOException {
		HashMap<String, String> request = new HashMap<String, String>();
		request.put("Type", "POST");
		request.put("URI", "/form");
		assertEquals(responder.respond(request, writer), twoHundred);
	}
	
	@Test
	public void testPutResponse() throws IOException {
		HashMap<String, String> request = new HashMap<String, String>();
		request.put("Type", "PUT");
		request.put("URI", "/form");
		assertEquals(responder.respond(request, writer), twoHundred);
	}
	
	@Test
	public void testFourOhFour() throws IOException {
		HashMap<String, String> request = new HashMap<String, String>();
		request.put("Type", "GET");
		request.put("URI", "/foo");
		assertEquals(responder.respond(request, writer), fourOhFour);
	}
}

