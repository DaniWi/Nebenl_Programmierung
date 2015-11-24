package sharing_objects;

public class MySafeListener {
	
	private final EventListener listener;
	
	private MySafeListener() {
		listener = new EventListener() {
			public void onEvent(ImmutableEvent e) {
				System.out.println(e.toString());
			}
		};
	}
	
	public static MySafeListener newInstance(EventGeneratorThread source) {
		MySafeListener safe = new MySafeListener();
		source.registerListener(safe.listener);
		return safe;
	}

}
