package producer_consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

	private static final Lock lock = new ReentrantLock();
	private static final Condition noEmptyBuffer = lock.newCondition();

	private static List<Buffer> activeBuffers = new ArrayList<Buffer>();

	public static void main(String[] args) {
		/* Create 4 buffer */
		Buffer buffer_A = new Buffer("A");
		Buffer buffer_B = new Buffer("B");
		Buffer buffer_C = new Buffer("C");
		Buffer buffer_D = new Buffer("D");

		/* add to active buffers */
		activeBuffers.add(buffer_A);
		activeBuffers.add(buffer_B);
		activeBuffers.add(buffer_C);
		activeBuffers.add(buffer_D);

		/* Create producer and consumer */
		Producer producer_A = new Producer(buffer_A, "Producer A");
		Producer producer_B = new Producer(buffer_B, "Producer B");
		Producer producer_C = new Producer(buffer_C, "Producer C");
		Producer producer_D = new Producer(buffer_D, "Producer D");
		Consumer consumer1 = new Consumer("Consumer 1");
		Consumer consumer2 = new Consumer("Consumer 2");

		/* register buffer at consumer */
		consumer1.register(buffer_A);
		consumer1.register(buffer_B);
		consumer1.register(buffer_C);
		consumer1.register(buffer_D);

		consumer2.register(buffer_A);
		consumer2.register(buffer_B);

		/* Create thread objects */
		Thread threadP_A = new Thread(producer_A);
		Thread threadP_B = new Thread(producer_B);
		Thread threadP_C = new Thread(producer_C);
		Thread threadP_D = new Thread(producer_D);
		Thread threadC_1 = new Thread(consumer1);
		Thread threadC_2 = new Thread(consumer2);

		/* start the threads */
		threadP_A.start();
		threadP_B.start();
		threadP_C.start();
		threadP_D.start();
		threadC_1.start();
		threadC_2.start();

	}

	public static List<Buffer> consume(List<Buffer> bufferList, String consumerName) {
		lock.lock();
		try {
			/* as long as there are empty buffers the consumer has to wait */
			while (isOneEmpty(bufferList)) {
				try {
					noEmptyBuffer.await();
					/*
					 * important! if consumer1 consumes a 0 and removes the buffer from his list consumer2 does not know
					 * about it => refresh active buffers list. retianAll computes the intersection of 2 lists.
					 */
					bufferList.retainAll(activeBuffers);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			/* if condition is fulfilled we can consume from every buffer */
			int size = bufferList.size();
			for (int i = 0; i < size; i++) {
				int number = bufferList.get(i).consume();
				System.out.println(consumerName + ": I got " + number + " " + bufferList.get(i).getName());
				if (number == 0) {
					/*
					 * if buffer contains a 0 this buffer should not be contacted any more. remove from bufferList =>
					 * for this consumer. remove from activeBuffers => to inform other consumers (see try above!
					 * bufferList.retainAll(activeBuffers) )
					 */
					activeBuffers.remove(bufferList.get(i));
					bufferList.remove(i);
					/* refresh size variable in order not to get IndexOutOfBoundException */
					size = bufferList.size();
				}
			}
		}
		finally {
			lock.unlock();
		}

		/* refreshed list of buffers is returned in order not to access buffers any more that had a 0 */
		return bufferList;
	}

	/*
	 * if a producer put a number into the buffer a signal is produced
	 */
	public static void signal() {
		lock.lock();
		try {
			noEmptyBuffer.signalAll();
		}
		finally {
			lock.unlock();
		}
	}

	/*
	 * checks for a given list of buffers if all contain at least one element returns: true - if at least one buffer is
	 * empty false - if all buffers contain at least one element
	 */
	public static boolean isOneEmpty(List<Buffer> bufferList) {
		for (Buffer buffer : bufferList) {
			if (buffer.isEmpty()) {
				return true;
			}
		}
		return false;
	}

}
