package futuretask;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.concurrent.ExecutionException;

/**
 * 
 * Main class that starts Threads with FutureTasks to approximate Pi
 * by Leibniz formula (https://en.wikipedia.org/wiki/Leibniz_formula_for_%CF%80)
 *
 */
public class Main {
	
	public static BigDecimal bd_four = new BigDecimal("4.0");
	public static double precision = 1e-5;
	
	/**
	 * 
	 * @param args: number of threads to be created
	 */
	public static void main(String[] args) {
		int threadCount = 1;
		if (args.length > 0) {
			threadCount = Integer.valueOf(args[0]);
		}
		
		System.out.println(threadCount + " Thread(s)");
		System.out.println("precision: " + precision);
		System.out.println("-----------------------");
		
		// cumulative variable for Pi approximation
		BigDecimal cum_pi = new BigDecimal("0", MathContext.DECIMAL128);
		CalculatePartOfPi[] threads = new CalculatePartOfPi[threadCount];
		Integer leibnizIndex = 0; // range index for leibniz formula of Pi
		double delta = 1.0;	// difference between pi and cum_pi
		
		long timeStart = System.currentTimeMillis();
		
		for (int i = 0; i < threadCount; i++) {
			threads[i] = new CalculatePartOfPi();
		}
		
		
		
		while (delta > precision) {
			// start all threads immediately after another
			for (int i = 0; i < threadCount; i++) {
				threads[i].start(leibnizIndex, leibnizIndex+4);
				leibnizIndex += 5;
			}
			
			// fetch results
			for (int i = 0; i < threadCount; i++) {
				try {
					cum_pi = cum_pi.add(threads[i].get());
				}
				catch (InterruptedException e) {
					System.out.println("Thread was interrupted!");
					e.printStackTrace();
				}
				catch (ExecutionException e) {
					System.out.println("Thread threw exception during computation!");
					e.printStackTrace();
				}
			}

			delta = Math.abs(cum_pi.multiply(bd_four).doubleValue() - Math.PI);
		}
		long timeEnd = System.currentTimeMillis();
		double timeResult = (timeEnd - timeStart)/(double)1000;
		
		System.out.println("Pi:     " + Math.PI);
		System.out.println("approx: " + cum_pi.multiply(bd_four));
		System.out.println("delta:  " + delta);
		System.out.println("time:   " + timeResult);
	}
}
