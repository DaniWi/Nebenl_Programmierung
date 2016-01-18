package linkedblockingdeque;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ComputingCentre implements Runnable {

	private int power;
	private int salary;
	private int id;
	private LinkedBlockingDeque<Job> deque;
	private LinkedBlockingDeque<Job> deque2;
	private LinkedBlockingDeque<Job> deque3;
	
	// constructor
	public ComputingCentre(int power, LinkedBlockingDeque<Job> deque, int id) {
		this.power = power;
		this.deque = deque;
		this.id = id;
	}

	// getters
	public int getPower() {
		return power;
	}

	public int getId() {
		return id;
	}

	public int getSalary() {
		return salary;
	}

	// setters
	public void setPower(int power) {
		this.power = power;
	}

	public void setDeque2(LinkedBlockingDeque<Job> deque2) {
		this.deque2 = deque2;
	}

	public void setDeque3(LinkedBlockingDeque<Job> deque3) {
		this.deque3 = deque3;
	}

	// add new job to deque
	public void addJob(Job job) {
		deque.addFirst(job);

		System.out.println("Job " + job.getId() + " added to centre " + this.id);
	}

	@Override
	public void run() {

		System.out.println("Thread " + this.id + " started");

		//do until interrupt and finish deque
		while (!Thread.currentThread().isInterrupted() || !deque.isEmpty()) {

			Job job = null;

			//take element from deque
			job = deque.pollLast();

			// own deque is empty..try to steal from the others
			if (job == null) {
				
				//get elements from other deques
				Job d2 = deque2.peekFirst();
				Job d3 = deque3.peekFirst();
				
				//only d3 available...take it
				if(d2 == null && d3 != null){
					job = deque3.pollFirst();
				//only d2 availabel...take it
				} else if(d3 == null && d2 != null){
					job = deque2.pollFirst();
				//jobs in both available
				}else if(d2 != null && d3 != null){
					//d2 has the better price
					if(d2.getPrice() < d3.getPrice()){
						job = deque2.pollFirst();
					//d3 has the better price
					}else{
						job = deque3.pollFirst();
					}
					
				}
				
				//output
				if(job != null){
					System.out.println("Centre " + this.id + " stole job " + job.getId());
				}
				
			}

			// no jobs in deques
			if (job == null) {
				continue;
			}

			// work time...
			try {
				long time = (long) (((double) job.getWork() / power) * 1000);

				Thread.sleep(time);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}

			// add salary for job
			this.salary += job.getPrice();

			// output
			System.out.println("Centre " + this.id + " finished Job " + job.getId() + " for " + job.getPrice());

		}

		System.out.println("Centre " + this.id + " finished and earned " + this.salary);

	}

}
