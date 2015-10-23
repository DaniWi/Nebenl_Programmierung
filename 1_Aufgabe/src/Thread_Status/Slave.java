package Thread_Status;

public class Slave implements Runnable{

	private int status;
	private Boolean running = true;
	public Thread joinThread;
	public Synchronize syn;
	
	@Override
	public void run() {
		
		while(this.running == true){
			switch (this.status){
			//running/blocking
			case 1:
				if(syn != null){
					syn.doSomething(Thread.currentThread().getId());
				}
				break;
			//sleep
			case 2:
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			//waiting
			case 3:
				if(joinThread != null){
					try {
						joinThread.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			//terminated
			case 4:
				this.running = false;
				break;
			};
		}
		
	}

	//set running
	public void setRunning(boolean run){
		this.running = run;
	}
	
	//set status
	public void setStatus(int status){
		this.status = status;
	}
	
}
