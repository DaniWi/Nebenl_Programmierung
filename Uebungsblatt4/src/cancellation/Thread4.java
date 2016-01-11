package cancellation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Thread4 takes the values from a server socket and print then the values to the console.
 * This thread must be terminated with an interrupt. The interrupt will be restored.
 * @author Witsch Daniel
 *
 */
public class Thread4 extends Thread {
	private ServerSocket socket;
	private Socket client;
	private BufferedReader reader = null;
	
	/**
	 * 
	 * @param socket server socket for the receiving of the values
	 */
	public Thread4(ServerSocket socket) {
		super();
		this.socket = socket;
		try {
			client = socket.accept();
			reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run(){
		while(!Thread.currentThread().isInterrupted()){
			try {
				Integer value = reader.read();
				System.out.println("Thread4 takes value " + value + " from socket");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Thread.sleep((long)(Math.random()*3000));
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
		}
		try {
			reader.close();
			client.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Thread4 terminated");
	}
	
	
}
