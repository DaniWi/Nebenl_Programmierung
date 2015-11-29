package composing_objects;

import java.util.concurrent.atomic.*;

/**
 * NumberRange
 * <p/>
 * Number range class that does not sufficiently protect its invariants
 *
 * @author Brian Goetz and Tim Peierls
 */

public class NumberRange {
    // INVARIANT: lower <= upper
    private final AtomicInteger lower = new AtomicInteger(0);
    private final AtomicInteger upper = new AtomicInteger(0);

    public synchronized void setLower(int i) {
        // Warning -- unsafe check-then-act
        if (i > upper.get()) {
        	throw new IllegalArgumentException("can't set lower to " + i + " > upper(" + upper.get() + ")");
        }
        lower.set(i);
    }

    public synchronized void setUpper(int i) throws InterruptedException {
        // Warning -- unsafe check-then-act
        if (i < lower.get()) {
        	Thread.sleep(2000);
        	throw new IllegalArgumentException("can't set upper to " + i + " < lower(" + lower.get()  + ")");
        }
        upper.set(i);
    }

    public synchronized boolean isInRange(int i) throws InterruptedException {
    	//to simulate scheduling between the 2 checks (lower and upper)
    	boolean low = i >= lower.get();
		Thread.sleep(1000);
    	boolean high = i <= upper.get();
    	// return (i >= lower.get() && i <= upper.get());
        return (low && high);
    }
}

