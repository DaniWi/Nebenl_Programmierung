package Thread_Status;

public class Thread_Report {

	public static void main(String[] args) {

		Synchronize syn = new Synchronize();

		// running thread
		Slave running = new Slave();
		running.syn = syn;
		running.setStatus(1);
		Thread runningThread = new Thread(running);

		// blocking thread
		Slave blocking = new Slave();
		blocking.setStatus(1);
		blocking.syn = syn;
		Thread blockingThread = new Thread(blocking);

		// terminated thread
		Slave terminated = new Slave();
		terminated.setStatus(4);
		Thread terminatedThread = new Thread(terminated);

		// sleeping thread
		Slave sleep = new Slave();
		sleep.setStatus(2);
		Thread sleepThread = new Thread(sleep);

		// waiting thread
		Slave join = new Slave();
		join.setStatus(3);
		join.joinThread = sleepThread;
		Thread joinThread = new Thread(join);

		// new thread
		Slave newSlave = new Slave();
		Thread newThread = new Thread(newSlave);

		// create and start monitor thread
		Monitor monitor = new Monitor();

		// pass terminated and new thread to monitor
		monitor.setTerminatedThread(terminatedThread);
		monitor.setNewThread(newThread);
		Thread moni_thread = new Thread(monitor);

		// start monitor thread
		moni_thread.start();

		// start other threads
		runningThread.start();
		terminatedThread.start();
		sleepThread.start();
		joinThread.start();
		blockingThread.start();

		// wait 20 seconds
		try{
			Thread.sleep(20000);
		} catch (InterruptedException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// stop thread execution
		running.setRunning(false);
		terminated.setRunning(false);
		sleep.setRunning(false);
		join.setRunning(false);
		blocking.setRunning(false);

		// stop monitor thread
		monitor.setRunning(false);

		try {
			runningThread.join();
			System.out.println("Running Thread returned");
			terminatedThread.join();
			System.out.println("Terminated Thread returned");
			sleepThread.join();
			System.out.println("Timed_waiting Thread returned");
			blockingThread.join();
			System.out.println("Blocking Thread returned");
			joinThread.join();
			System.out.println("Waiting Thread returned");
			moni_thread.join();
			System.out.println("\n");
			System.out.println("Monitor Thread returned");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
