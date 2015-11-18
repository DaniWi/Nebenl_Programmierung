package thread_safety_b;

public class Master {

	public static void main(String[] args) {

		LazyInitRace lir = new LazyInitRace();
		
		// create slave
		Slave slave1 = new Slave();
		Slave slave2 = new Slave();
		Slave slave3 = new Slave();

		slave1.setLir(lir);
		slave2.setLir(lir);
		slave3.setLir(lir);
		
		// create threads
		Thread thread1 = new Thread(slave1);
		Thread thread2 = new Thread(slave2);
		Thread thread3 = new Thread(slave3);

		thread1.start();
		thread2.start();
		thread3.start();
		
		// wait for threads to be finished
		try {
			//thread1.start();
			thread1.join();
			//thread2.start();
			thread2.join();
			//thread3.start();
			thread3.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(slave1.getEo().toString());
		System.out.println(slave2.getEo().toString());
		System.out.println(slave3.getEo().toString());
		
		// check object counter
		System.out.println("Number of objects: " + ExpensiveObject.getCount());

	}

}
