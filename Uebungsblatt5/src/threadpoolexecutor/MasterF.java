package threadpoolexecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MasterF {

	private static final int CAPACITY = 200;
	private static final int N_THREADS = 200;

	public static void main(String[] args) {

		int start = 1;
		Double result = 0d;
		
		final ThreadPoolExecutor executor = new ThreadPoolExecutor(N_THREADS, N_THREADS, 0L, TimeUnit.MILLISECONDS,
		                              new LinkedBlockingQueue<Runnable>(CAPACITY));
		
		ExecutorService es = Executors.unconfigurableExecutorService(executor);
		
		CompletionService<Double> service = new ExecutorCompletionService<Double>(es);

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

		es.shutdown();
		
		System.out.println("Result: " + result);
		System.out.println("Time: " + timeResult);

	}

}
