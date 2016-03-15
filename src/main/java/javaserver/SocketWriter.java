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
		System.out.println(response);
	}

	public void closeOutputStream() {
		try {
			this.writingMechanism.close();
		} catch (IOException e) {
			System.out.println("SocketWriter was unable to close output stream");
		}
		this.isOutputStreamOpen = false;
	}
}

