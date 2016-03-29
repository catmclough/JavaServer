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

	public void respond(String response) {
		try {
			writingMechanism.writeBytes(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.latestResponse = response;
		closeOutputStream();
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

