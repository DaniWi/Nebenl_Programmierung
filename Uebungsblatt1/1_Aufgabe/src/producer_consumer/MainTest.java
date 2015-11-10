package producer_consumer;

public class MainTest {

	public static void main(String[] args) {
		Buffer buffer = new Buffer();
		Producer producer = new Producer(buffer);
		Consumer consumer = new Consumer(buffer);

		Thread threadP = new Thread(producer);
		Thread threadC = new Thread(consumer);

		threadP.start();
		threadC.start();

	}

}
