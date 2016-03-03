package javaserver;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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
		assertEquals(responder.respond("GET", "/", writer), responder.TWO_HUNDRED);
	}
	
	@Test
	public void testFourOhFour() throws IOException {
		assertEquals(responder.respond("GET", "/foobar", writer), responder.FOUR_OH_FOUR);
	}
}

