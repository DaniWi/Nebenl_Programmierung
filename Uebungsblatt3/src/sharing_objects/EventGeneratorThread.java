package sharing_objects;

import java.util.ArrayList;

public class EventGeneratorThread extends Thread {

	private ThreadLocal<ArrayList<EventListener>> registeredListeners = new ThreadLocal<ArrayList<EventListener>>();
	
	public void registerListener(EventListener listener) {
		
	}
	
	public void infoListeningObjects() {
		// prints content of registeredListeners list
		
	}
}
