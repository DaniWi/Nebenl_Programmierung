package producer_consumer;

import java.util.ArrayList;
import java.util.List;

public class Consumer implements Runnable {

	private List<Buffer> registeredBuffers = new ArrayList<Buffer>();
	private String name;

	public Consumer(String name) {
		this.name = name;
	}

	public void register(Buffer buffer) {
		registeredBuffers.add(buffer);
	}

	public void unregister(Buffer buffer) {
		registeredBuffers.remove(buffer);
	}

	@Override
	public void run() {
		do {
			registeredBuffers = Main.consume(registeredBuffers, name);
		} while (registeredBuffers.size() > 0);

		System.out.println(name + " has finished!");
	}

}
