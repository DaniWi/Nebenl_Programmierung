package producer_consumer;

public class Buffer {

	private int size = 10;
	private int[] buffer = new int[size];
	private int produceIndex = 0;
	private int consumeIndex = 0;
	private boolean empty = true;

	synchronized public int consume() {

		if (!empty) {
			int value = buffer[consumeIndex];
			consumeIndex = (consumeIndex + 1) % size;
			if (consumeIndex == produceIndex) {
				empty = true;
			}
			return value;
		}

		return -1;
	}

	synchronized public void produce(int number) {
		if (consumeIndex == produceIndex && !empty) {
			// buffer is full
			System.out.println("Buffer full: couldn't produce " + number);
		}

		buffer[produceIndex] = number;
		produceIndex = (produceIndex + 1) % size;
		empty = false;
	}

}
