package javaserver;

public class ThreadManager {

	public void openNewThread(Reader reader, SocketWriter writer) {
		ClientWorker clientWorker = new ClientWorker(reader, writer);
		Thread t = createNewThread(clientWorker);
		startThread(t);
	}

	private Thread createNewThread(ClientWorker clientWorker) {
		return new Thread(clientWorker);
	}

	private void startThread(Thread thread) {
		thread.run();
	}
}
