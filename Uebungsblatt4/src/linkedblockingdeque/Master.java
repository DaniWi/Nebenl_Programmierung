package linkedblockingdeque;

import java.util.concurrent.LinkedBlockingDeque;

public class Master {

	public static void main(String[] args) {

		//create deques
		LinkedBlockingDeque<Job> deque1 = new LinkedBlockingDeque<Job>();
		LinkedBlockingDeque<Job> deque2 = new LinkedBlockingDeque<Job>();
		LinkedBlockingDeque<Job> deque3 = new LinkedBlockingDeque<Job>();

		//create centres
		ComputingCentre centre1 = new ComputingCentre(500, deque1, 1);
		ComputingCentre centre2 = new ComputingCentre(80, deque2, 2);
		ComputingCentre centre3 = new ComputingCentre(20, deque3, 3);

		//set other deques		
		centre1.setDeque2(deque2);
		centre1.setDeque3(deque3);
		
		centre2.setDeque2(deque1);
		centre2.setDeque3(deque3);
		
		centre3.setDeque2(deque1);
		centre3.setDeque3(deque2);
		
		//create threads
		Thread thread1 = new Thread(centre1);
		Thread thread2 = new Thread(centre2);
		Thread thread3 = new Thread(centre3);
		
		//start threads
		thread1.start();
		thread2.start();
		thread3.start();
		
		//create 200 jobs
		for (int i = 0; i < 200; i++) {

			// sleep up to 0.05 seconds
			try {
				Thread.sleep((long) (Math.random() * 50));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//create new job
			Job job = createJob(i+1);

			int rand = (int) (Math.random() * 3) + 1;
				
			//add jobs to computing centre deques
			switch (rand) {
			case 1:
				centre1.addJob(job);
				break;
			case 2:
				centre2.addJob(job);
				break;
			case 3:
				centre3.addJob(job);
				break;
			}

		}
		
		//interrupt threads
		thread1.interrupt();
		thread2.interrupt();
		thread3.interrupt();
		
		//wait for threads to be finished
		try {
			thread1.join();
			thread2.join();
			thread3.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// create new job
	private static Job createJob(int id) {

		Job job = new Job((int) (Math.random() * 10), (int) (Math.random() * 1000), id);

		return job;
	}

}
