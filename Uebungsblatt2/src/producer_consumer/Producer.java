package producer_consumer;

public class Producer implements Runnable {

	private Buffer buffer;
	private String name;

	public Producer(Buffer buffer, String name) {
		this.buffer = buffer;
		this.name = name;
	}

	@Override
	public void run() {
		int randomNumber;
		do {
			randomNumber = (int) (101 * Math.random());
			System.out.println(name + ": I made " + randomNumber);
			buffer.produce(randomNumber);
			Main.signal();
			try {
				Thread.sleep((long) (3000 * Math.random()));
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (randomNumber != 0);

		System.out.println(name + " has finished!");

	}

}
