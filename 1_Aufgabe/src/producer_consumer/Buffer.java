package producer_consumer;

public class Buffer {

	int size = 10;
	int[] buffer = new int[size];
	int index = 0;

	synchronized public int consume() {

		if (index > 0) {
			// System.out.println(buffer[index - 1] + " consumed.");
			return buffer[--index];
		}

		return -1;
	}

	synchronized public void produce(int number) {
		if (index >= size) {
			// buffer is full -> double size
			int[] newBuffer = new int[2 * size];
			for (int i = 0; i < index; i++) {
				newBuffer[i] = buffer[i];
			}
			buffer = newBuffer;
			size = 2 * size;
		}

		buffer[index] = number;
		index++;
		// System.out.println(number + " produced.");
	}

}
