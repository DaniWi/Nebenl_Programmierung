package producer_consumer;

public class Consumer implements Runnable {

	private Buffer buffer;

	public Consumer(Buffer buffer) {
		this.buffer = buffer;
	}

	@Override
	public void run() {
		int number;
		do {
			number = buffer.consume();
			if (number == -1) {
				System.out.println("Consumer: Buffer empty! :'(");
				try {
					Thread.sleep(2000);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else {
				System.out.println("Consumer: I got " + number);
			}
		} while (number != 0);
	}

}
