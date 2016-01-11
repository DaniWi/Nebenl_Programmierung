package cancellation;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Thread2 takes the values from receiving message queue and sends only the even numbers to the next thread with the sending message queue.
 * Thread2 is terminated with the poison pill (-1) in the receiving message queue.
 * @author Witsch Daniel
 *
 */
public class Thread2 extends Thread {
	private ConcurrentLinkedQueue<Integer> messagesGet;
	private ConcurrentLinkedQueue<Integer> messagesPut;

	/**
	 * 
	 * @param messagesGet receiving message queue
	 * @param messagesPut sending message queue
	 */
	public Thread2(ConcurrentLinkedQueue<Integer> messagesGet,
			ConcurrentLinkedQueue<Integer> messagesPut) {
		super();
		this.messagesGet = messagesGet;
		this.messagesPut = messagesPut;
	}
	
	public void run(){
		while(true){
			Integer value = (Integer) messagesGet.poll();
			if(value != null){
				System.out.println("Thread2 takes value " + value + " from queue");
				//Poison pill
				if(value == -1){
					System.out.println("Thread 2: Poison Pill -1");
					break;
				}
				if(value%2 == 0){
					messagesPut.add(value);
					System.out.println("Thread2 added value " + value + " to queue");
				}
			}
			try {
				Thread.sleep((long)(Math.random()*3000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
