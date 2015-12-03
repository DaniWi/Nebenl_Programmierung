package sharing_objects;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Immutable Event class
 * final does not allow to subclass and override methods
 *
 */
final public class ImmutableEvent {

	private final String time;
	private final String source;
	
	public ImmutableEvent(String source) {
		this.time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
		this.source = source;
	}
	
	public String toString() {
		return "Event " + source + " [" + time + "]";
	}
}
