package Thread_Status;

import java.util.Iterator;
import java.util.Set;

public class Monitor implements Runnable {

	private Boolean running = true;
	private Thread terminated = null;
	private Thread newThread = null;
	
	@Override
	public void run() {

		while (this.running == true) {

			//sleep 3 seconds
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//get stack trace
			Set<Thread> threads = Thread.getAllStackTraces().keySet();
			
			//get thread iterator
			Iterator<Thread> it = threads.iterator();
			
			while(it.hasNext()){
				Thread thread = it.next();
				if(thread.getName().contains("Thread-") == false){
					continue;
				}
				System.out.println("Thread name: " + thread.getName() + " ;\t Thread status: " + thread.getState());
			}
			
			//terminated thread
			if(terminated != null){
				System.out.println("Thread name: " + terminated.getName() + " ;\t Thread status: " + terminated.getState());
			}
			//new thread
			if(newThread != null){
				System.out.println("Thread name: " + newThread.getName() + " ;\t Thread status: " + newThread.getState());
			}
			System.out.println("\n");

		}

	}

	//set running flag
	public void setRunning(Boolean run) {
		this.running = run;
	}
	
	//set terminated thread
	public void setTerminatedThread(Thread thread){
		this.terminated = thread;
	}
	
	//set new thread
	public void setNewThread(Thread thread){
		this.newThread = thread;
	}

}
