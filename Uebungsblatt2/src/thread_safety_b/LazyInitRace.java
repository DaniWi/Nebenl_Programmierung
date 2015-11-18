package thread_safety_b;

/**
 * LazyInitRace
 *
 * Race condition in lazy initialization
 *
 * @author Brian Goetz and Tim Peierls
 */

public class LazyInitRace {
	private ExpensiveObject instance = null;

	public ExpensiveObject getInstance() {
		if (instance == null) {
			synchronized (this) {
				if (instance == null) {
					instance = new ExpensiveObject();
				}
			}
		}
		return instance;
	}

}

class ExpensiveObject { 
	
	// counter for constructor call
	private static int count = 0;
	private static int wait = 4;

	public ExpensiveObject() {
		try {
			Thread.sleep(wait-- * 5000);
			synchronized (this) {
				++count;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//get counter
	public static int getCount(){
		return count;
	}
	
}
