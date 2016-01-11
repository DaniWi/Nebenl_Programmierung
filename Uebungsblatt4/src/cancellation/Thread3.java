package cancellation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Thread3 takes the values from a message queue and sends all values through a socket to an receiving thread.
 * This thread must be terminated with the terminate-method.
 * @author Witsch Daniel
 *
 */
public class Thread3 extends Thread {
	private ConcurrentLinkedQueue<Integer> messages;
	private volatile Boolean keepRunning = true;
	private Socket socket = null;
	private BufferedWriter writer = null;

	/**
	 * 
	 * @param messages receiving message queue
	 */
	public Thread3(ConcurrentLinkedQueue<Integer> messages) {
		super();
		this.messages = messages;
		try {
			socket = new Socket(InetAddress.getLoopbackAddress(), 8080);
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run(){
		while(keepRunning == true){
			Integer value = (Integer) messages.poll();
			if(value != null){
				System.out.println("Thread3 takes value " + value + " from queue");
				try {
					writer.write(value);
					System.out.println("Thread3 added value " + value + " to socket");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {

				}
			}
			try {
				Thread.sleep((long)(Math.random()*3000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			writer.flush();
			writer.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Thread3 terminated");
	}
	
	/**
	 * terminate thread 3
	 */
	public void terminate(){
		keepRunning = false;
	}
}
