package sharing_objects;

public class Main {

	public static void main(String[] args) {
		
		MySafeListener listener1 = MySafeListener.newInstance();
		MySafeListener listener2 = MySafeListener.newInstance();
		
		EventGeneratorThread source1 = new EventGeneratorThread();
		EventGeneratorThread source2 = new EventGeneratorThread();
		EventGeneratorThread source3 = new EventGeneratorThread();
		EventGeneratorThread source4 = new EventGeneratorThread();
		EventGeneratorThread source5 = new EventGeneratorThread();
		EventGeneratorThread source6 = new EventGeneratorThread();
		
		source1.addMySafeListener(listener1);
		source2.addMySafeListener(listener2);
		source3.addMySafeListener(listener1);
		source3.addMySafeListener(listener2);
		source4.addMySafeListener(listener1);
		source4.addMySafeListener(listener2);
		source5.addMySafeListener(listener1);
		source6.addMySafeListener(listener2);
		
		ListeningObjectsThread t1 = new ListeningObjectsThread();
		
		source1.start();
		source2.start();
		source3.start();
		source4.start();
		source5.start();
		source6.start();
		listener1.start();
		listener2.start();
		t1.start();
		
		try {
			Thread.sleep((long) 7e3);
		}
		catch (InterruptedException ignored) {}
		
		t1.interrupt();
		source1.interrupt();
		source2.interrupt();
		source3.interrupt();
		source4.interrupt();
		source5.interrupt();
		source6.interrupt();
	}

}
