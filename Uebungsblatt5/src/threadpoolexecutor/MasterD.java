package threadpoolexecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MasterD {

	private static final int CAPACITY = 200;
	private static final int N_THREADS = 200;

	public static void main(String[] args) {

		int start = 1;
		Double result = 0d;
		
		ThreadPoolExecutor executor = new ThreadPoolExecutor(N_THREADS, N_THREADS, 0L, TimeUnit.MILLISECONDS,
		                              new LinkedBlockingQueue<Runnable>(CAPACITY));
		
		CompletionService<Double> service = new ExecutorCompletionService<Double>(executor);

		long timeStart = System.currentTimeMillis();
		
		for (int i = 0; i < CAPACITY; start += 8000, i++) {
			MCS mcs = new MCS(start);
			try {
					service.submit(mcs);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		for(int i = 0; i < CAPACITY; i++){
			try {
				result += service.take().get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		long timeEnd = System.currentTimeMillis();
		double timeResult = (timeEnd - timeStart)/(double)1000;

		executor.shutdown();
		
		System.out.println("Result: " + result);
		System.out.println("Time: " + timeResult);

	}

}
