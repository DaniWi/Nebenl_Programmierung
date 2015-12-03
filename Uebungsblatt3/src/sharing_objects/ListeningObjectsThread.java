package sharing_objects;

public class ListeningObjectsThread extends Thread {

	/*
	 * creates every 4s threads, asking the sources to print their listener list 
	 */
	@Override
	public void run() {
		while (!isInterrupted()) {
			for (Thread t : Thread.getAllStackTraces().keySet()) {
				if (t instanceof EventGeneratorThread) {
				
					new Thread(new Runnable(){
							public void run() {
								((EventGeneratorThread) t).infoListeningObjects();
							}}).start();
				}
			}
			
			try {
				Thread.sleep(4000);
			}
			catch (InterruptedException e) {
				return;
			}
		}
	
	}
}
