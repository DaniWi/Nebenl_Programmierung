package Java_Threads;

public class Slave implements Runnable {

	@Override
	public void run() {
		
		//print current thread id
		System.out.println("Thread " + Thread.currentThread().getId() + " is running");
		
	}

}
