package javaserver;

import static org.mockito.Mockito.spy;

import java.io.IOException;

import org.junit.BeforeClass;

import junit.framework.TestCase;

public class AppTest extends TestCase{
	App app;
	ServerFactory sf;
	static int defaultPort = 5000;

	@BeforeClass
	public void setUp() {
		this.app = spy(new App());
	}
	
	public void testDefaultPort() {
		assertEquals(defaultPort, App.PORT);
	}
	
	public void testMain() throws IOException {
		assertEquals(App.server, null);
		//TODO: Test that server var gets set without creating real Server
//		App.main(null);
//		assertTrue(App.server != null);
	}
}
