package threadpoolexecutor;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MasterH {

	private static final int CAPACITY = 50;
	private static final int N_THREADS = 50;
	private static final int I = 200;

	public static void main(String[] args) {

		int start = 1;
		Double result = 0d;
		
		//create queue
		LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(CAPACITY);
		
		//create executor
		final ThreadPoolExecutor executor = new ThreadPoolExecutor(N_THREADS, N_THREADS, 0L, TimeUnit.MILLISECONDS,
				queue);
		
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
		
		//create unconfigurable executor service
		ExecutorService es = Executors.unconfigurableExecutorService(executor);
		
		//create completion service
		CompletionService<Double> service = new ExecutorCompletionService<Double>(es);

		long timeStart = System.currentTimeMillis();

		//add all tasks to the completion service
		for (int i = 0; i < I; start += 8000, i++) {
			MCS mcs = new MCS(start);
			try {
				service.submit(mcs);
			} catch (RejectedExecutionException e) {
				System.out.println("rejected");
				//wait until queue is not full
				while(queue.size() == CAPACITY);
				//add element again
				service.submit(mcs);
			}
		}
		
		//take remaining elements
		for(int i = 0; i < I; i++){
			try {
				result += service.take().get();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		long timeEnd = System.currentTimeMillis();
		double timeResult = (timeEnd - timeStart)/(double)1000;

		//shutdown executor service
		es.shutdown();
		
		System.out.println("Result: " + result);
		System.out.println("Time: " + timeResult);

	}

}
