package sharing_objects;

public class MySafeListener extends Thread {
	
	private final EventListener listener;
	private static int ID = 1;
	private final int threadID;
	
	private MySafeListener() {
		threadID = ID++;
		listener = new EventListener() {
			public void onEvent(ImmutableEvent e) {
				onEventMethod(e);
			}
		};
	}
	
	public static MySafeListener newInstance(EventGeneratorThread... generators) {
		MySafeListener safe = new MySafeListener();
		return safe;
	}
	
	private void onEventMethod(ImmutableEvent e) {
		System.out.println("Listener" + threadID + ": " + e.toString());
	}
	
	public void registerMyListener(EventGeneratorThread source) {
		source.registerListener(listener);
	}
}
