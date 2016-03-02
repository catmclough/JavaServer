package javaserver;

import java.io.DataOutputStream;
import java.io.IOException;

public class SocketWriter {
	private DataOutputStream writingMechanism;
	public String latestResponse;

	SocketWriter(DataOutputStream dataOutput) {
		this.writingMechanism = dataOutput;
	}

	public void respond(String response) throws IOException {
		writingMechanism.writeBytes(response);
		writingMechanism.close();
		this.latestResponse = response;
	}
}

