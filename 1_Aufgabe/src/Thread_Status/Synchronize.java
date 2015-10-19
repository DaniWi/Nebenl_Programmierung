package Thread_Status;

public class Synchronize {

	public synchronized void doSomething(long id){
		
		//get start time
		long start = System.currentTimeMillis();
		
		while(true){

			//get current time
			long current = System.currentTimeMillis();
			
			//stop after 10 seconds
			if((current - start) > 10000){
				break;
			}
		}
	}

}
