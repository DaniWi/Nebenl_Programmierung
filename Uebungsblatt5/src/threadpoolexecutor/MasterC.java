package threadpoolexecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MasterC {

	private static final int CAPACITY = 200;

	public static void main(String[] args) {

		int start = 1;
		Double result = 0d;
		
		long timeStart = System.currentTimeMillis();
		
		for (int i = 0; i < CAPACITY; start += 8000, i++) {
			MCS mcs = new MCS(start);
			try {
				result += mcs.call();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		long timeEnd = System.currentTimeMillis();
		double timeResult = (timeEnd - timeStart)/(double)1000;
		
		System.out.println("Result: " + result);
		System.out.println("Time: " + timeResult);

	}

}
