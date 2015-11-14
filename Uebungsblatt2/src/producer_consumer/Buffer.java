package producer_consumer;

public class Buffer {
	private int size = 10;
	private int[] buffer = new int[size];
	private int produceIndex = 0;
	private int consumeIndex = 0;
	private volatile boolean empty = true;
	private final int enlargeSize = 10;
	private String name;

	public Buffer(String name) {
		this.name = name;
	}

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
			resize();
		}
		buffer[produceIndex] = number;
		produceIndex = (produceIndex + 1) % size;
		empty = false;
	}

	public boolean isEmpty() {
		return empty;
	}

	/*
	 * enlarges the buffer by enlargeSize elements
	 */
	private void resize() {
		int[] newBuffer = new int[size + enlargeSize];
		for (int i = 0; i < size; i++) {
			newBuffer[i] = buffer[(consumeIndex + i) % size];
		}
		consumeIndex = 0;
		produceIndex = size;
		size += enlargeSize;

		buffer = newBuffer;
	}

	public String getName() {
		return "(" + this.name + ")";
	}
}
