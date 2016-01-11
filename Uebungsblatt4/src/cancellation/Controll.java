package cancellation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Controll {

	public static void main(String[] args) throws InterruptedException {
		ConcurrentLinkedQueue<Integer> queue1 = new ConcurrentLinkedQueue<>();
		ConcurrentLinkedQueue<Integer> queue2 = new ConcurrentLinkedQueue<>();
		ServerSocket socket = null;
		try {
			socket = new ServerSocket(8080);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Thread1 thread1 = new Thread1(queue1);
		Thread2 thread2 = new Thread2(queue1, queue2);
		Thread3 thread3 = new Thread3(queue2);
		Thread4 thread4 = new Thread4(socket);
		
		System.out.println("Controll: Threads created");
				
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		
		System.out.println("Controll: Threads started");
		
	    InputStreamReader input  = new InputStreamReader(System.in);
	 
	    BufferedReader keyboardInput = new BufferedReader(input);
	    
	    try {
			keyboardInput.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
	    System.out.println("Controll: User terminates Thread1");
	    
	    thread1.terminate();
		
	    //wait for thread1 and thread2 to terminate
		thread1.join();
		thread2.join();
		
		//if both threads are terminated, controll thread terminate thread3
		thread3.terminate();
		
		System.out.println("Controll: Thread3 terminated");
		
		thread3.join();

		System.out.println("Controll: Thread4 terminate with interrupt");
		
		thread4.interrupt();

		thread4.join();
		
		System.out.println("Controll: All Threads joined");
		
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Controll: ServerSocket closed");
	}

}
