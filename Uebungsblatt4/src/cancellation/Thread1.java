package cancellation;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Thread1 creates numbers between 0-41 and sends this values via the message queue to another thread.
 * The thread must be terminated with the terminate-method. When the thread will be terminated, the last message in the queue is
 * the poison pill (-1) for the next thread.
 * @author Witsch Daniel
 *
 */
public class Thread1 extends Thread {
	private ConcurrentLinkedQueue<Integer> messages;
	private volatile Boolean keepRunning = true;

	/**
	 * 
	 * @param messages message queue for communication with Thread2
	 */
	public Thread1(ConcurrentLinkedQueue<Integer> messages) {
		super();
		this.messages = messages;
	}
	
	public void run(){
		while(keepRunning == true){
			Integer value = (int) ((Math.random()*41));
			messages.add(value);
			System.out.println("Thread1 added value " + value + " to queue");
			try {
				Thread.sleep((long)(Math.random()*3000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		messages.add(-1);
		System.out.println("Thread1 added value -1 to queue (poison pill)");
	}
	
	/**
	 * terminates this thread and sends a poison pill to the next thread via the message queue
	 */
	public void terminate(){
		keepRunning = false;
	}
}
