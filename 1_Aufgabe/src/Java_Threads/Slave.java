package Java_Threads;

public class Slave implements Runnable {

	@Override
	public void run() {

		// print current thread id
		System.out.println("Thread " + Thread.currentThread().getId() + " is running");

		try {
			Thread.sleep(1000000);
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
