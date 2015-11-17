package thread_safety_a;

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
        if (instance == null)
            instance = new ExpensiveObject();
        return instance;
    }

}

class ExpensiveObject { 
	
	//counter for constructor call
	private static int count = 0;
	
	public ExpensiveObject(){
		try {
			Thread.sleep(++count*5000);
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

