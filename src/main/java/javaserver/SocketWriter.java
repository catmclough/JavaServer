package javaserver;

import java.io.DataOutputStream;
import java.io.IOException;

public class SocketWriter {
	private DataOutputStream writingMechanism;
	public boolean isOutputStreamOpen = true;
	public String latestResponse;

	SocketWriter(DataOutputStream dataOutput) {
		this.writingMechanism = dataOutput;
	}

	public void respond(String response) throws IOException {
		writingMechanism.writeBytes(response);
		this.latestResponse = response;
	}
	
	public void closeOutputStream() throws IOException {
		this.writingMechanism.close();
		this.isOutputStreamOpen = false;
	}
}

