package javaserver;

import java.io.DataOutputStream;
import java.io.IOException;

public class SocketWriter {
	private DataOutputStream writingMechanism;

	SocketWriter(DataOutputStream dataOutput) {
		this.writingMechanism = dataOutput;
	}
	
	public void respond(String response) throws IOException {
		writingMechanism.writeBytes(response); 
	}

	public void close() throws IOException {
		writingMechanism.close();
	}
}
