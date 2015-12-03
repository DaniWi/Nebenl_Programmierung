package sharing_objects;

import java.util.ArrayList;

public class EventGeneratorThread extends Thread {

	private final ArrayList<MySafeListener> mySafeListenerList = new ArrayList<MySafeListener>();
	
	// ThreadLocal Object :
	// initialValue is called at first .get call or at a .get call after a .remove call
	private final ThreadLocal<ArrayList<EventListener>> registeredListeners = new ThreadLocal<ArrayList<EventListener>>() {
		@Override public ArrayList<EventListener> initialValue() {
            return new ArrayList<EventListener>();
        }
	};
	
	private static int ID = 1;
	private final String source;
	private Integer infoCount = 0;
	
	public EventGeneratorThread() {
		source = "Source" + ID++;
	}
	
	public void addMySafeListener(MySafeListener l) {
		mySafeListenerList.add(l);
	}
	
	public void registerListener(EventListener listener) {
		ArrayList<EventListener> list = registeredListeners.get();
		list.add(listener);
		registeredListeners.set(list);
	}
	
	public void infoListeningObjects() {
		synchronized(infoCount){
			infoCount++;
		}
	}
	
	private void printListeningObjects() {
		synchronized(infoCount) {
			while(infoCount > 0) {
				System.out.println("== " + source + " Info == (size: " + registeredListeners.get().size() + ")");
				for(EventListener l : registeredListeners.get()) {
					System.out.println("== " + l.toString());
				}
				infoCount--;
			}
		}
	}
	
	/*
	 * run method
	 * thread-interrupt terminates this thread
	 */
	public void run() {
		// copy listeners from mySafeListenerList to ThreadLocal Array so EventGeneratorThread has them in the ThreadLocal
		for (MySafeListener listener : mySafeListenerList) {
			listener.registerMyListener(this);
		}
		
		while(!isInterrupted()) {
			// create a new immutable event and notify all listeners
			final ImmutableEvent e = new ImmutableEvent(source);
			System.out.println("New " + e.toString());
			for (EventListener listener : registeredListeners.get()) {
				listener.onEvent(e);
			}
			
			/* pause of 3s
			 * check in the meanwhile if some ListeningObjectsThreads have called infoListeningObjects
			 * => print listener list
			 */
			long t = System.currentTimeMillis();
			while (System.currentTimeMillis() < t + 3000 && !isInterrupted()) {
				printListeningObjects();
				
				try {
					Thread.sleep(500);
				}
				catch (InterruptedException ex) {
					return;
				}
			}
		}
	}
}
