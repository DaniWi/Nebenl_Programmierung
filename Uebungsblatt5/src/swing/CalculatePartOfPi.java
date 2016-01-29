package swing;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CalculatePartOfPi {

	private int start;
	private int end;
	
	public CalculatePartOfPi(int start, int end) {
		this.start = start;
		this.end = end;
	}
	
	private final FutureTask<Double> futureTask = 
			new FutureTask<Double>(new Callable<Double>() {
				public Double call() {
					return calculatePartOfPi();
				}
			});
	
	private final Thread thread = new Thread(futureTask);
	
	public void start() {
		thread.start();
	}
	
	public double get() {
		try {
			return futureTask.get();
		}
		catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private Double calculatePartOfPi() {
		Double result = 0.0;
		for(int n = start; n <= end; n++)
		{
			if(n%2 == 0) {
				result += 1.0/(2*n+1);
			}
			else {
				result -= 1.0/(2*n+1);
			}			
		}
		return result;
	}

	
}
