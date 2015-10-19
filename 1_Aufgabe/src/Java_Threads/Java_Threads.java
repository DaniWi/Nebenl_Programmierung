package Java_Threads;

public class Java_Threads {

	public static void main(String[] args) {

		int thread_count = 0;
		
		//default
		if(args.length == 0){
			thread_count = 8;
		//configuration
		} else {
			thread_count = Integer.parseInt(args[0]);
		}

		//creating thread array
		Thread[] slaves = new Thread[thread_count];
		
		//creating slave object
		Slave temp = new Slave();
		
		for(int i = 0; i < thread_count; i++){
			
			//creating new thread
			Thread thread = new Thread(temp);
			
			//insert thread into array
			slaves[i] = thread;
			
			//start thread
			thread.start();
		}
		
		//waiting for all thread to be finished
		for(int i = 0; i < thread_count; i++){
			try {
				slaves[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
