package producer_consumer;

public class Producer implements Runnable {

	private Buffer buffer;

	public Producer(Buffer buffer) {
		this.buffer = buffer;
	}

	@Override
	public void run() {
		int randomNumber;
		do {
			randomNumber = (int) (101 * Math.random());
			System.out.println("Producer: I made " + randomNumber);
			buffer.produce(randomNumber);
			try {
				Thread.sleep((long) (3000 * Math.random()));
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (randomNumber != 0);

	}

}
