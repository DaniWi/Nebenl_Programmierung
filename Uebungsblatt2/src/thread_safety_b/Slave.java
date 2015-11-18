package thread_safety_b;

public class Slave implements Runnable {

	private ExpensiveObject eo = null;
	private LazyInitRace lir = null;
	
	@Override
	public void run() {
		
		//get expensive object instance
		this.eo = lir.getInstance();
	
	}

	//return expensive object
	public ExpensiveObject getEo() {
		return eo;
	}

	public void setLir(LazyInitRace lir) {
		this.lir = lir;
	}

}
