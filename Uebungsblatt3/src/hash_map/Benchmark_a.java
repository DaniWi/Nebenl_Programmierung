package hash_map;

import java.util.HashMap;
import java.util.concurrent.*;

public class Benchmark_a {

	private final static int DEFAULT_THREAD = 8;
	private final static int DEFAULT_ELEMENTS = 100;
	private final static int DEFAULT_METHOD = 1;

	public static void main(String[] args) {

		// create maps
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		HashMapSync mapSync = new HashMapSync();
		ConcurrentHashMap<Integer, Integer> concMap = new ConcurrentHashMap<Integer, Integer>();

		// set default values
		int thread_count = DEFAULT_THREAD;
		int elements     = DEFAULT_ELEMENTS;
		int method       = DEFAULT_METHOD;

		// read thread count
		try {
			if (args[0] != null) {
				thread_count = Integer.valueOf(args[0]);
			} else {
				System.out.println("thread count missing (arg0); default = " + DEFAULT_THREAD);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("thread count missing (arg0); default = " + DEFAULT_THREAD);
		}

		// read elements
		try {
			if (args[1] != null) {
				elements = Integer.valueOf(args[1]);
			} else {
				System.out.println("element count missing (arg1); default = " + DEFAULT_ELEMENTS);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("element count missing (arg1); default = " + DEFAULT_ELEMENTS);
		}

		// read method
		try {
			if (args[2] != null) {
				method = Integer.valueOf(args[2]);
			} else {
				System.out.println("method missing (arg2); default = " + DEFAULT_METHOD);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("method missing (arg2); default = " + DEFAULT_METHOD);
		}

		// creating thread array
		Thread[] threads = new Thread[thread_count];

		// creating slaves
		Slave[] slaves = new Slave[thread_count];

		int p = elements / 2 / thread_count;
		int c = elements / 4 / thread_count;
		int r = elements / 4 / thread_count;
		
		//Start time
		long timeStart = System.currentTimeMillis();
		
		for (int i = 0; i < thread_count; i++) {

			Thread thread = null;

			// creating new thread
			switch (method) {
			// HashMap
			case 1:
				Slave slaveMap = new Slave(map, c, r, p);
				slaves[i] = slaveMap;
				thread = new Thread(slaveMap);
				break;
			// HashMap synchronized
			case 2:
				Slave slaveMapSync = new Slave(mapSync, c, r, p);
				slaves[i] = slaveMapSync;
				thread = new Thread(slaveMapSync);
				break;
			// ConcurrentHashMap
			case 3:
				Slave slaveConc = new Slave(concMap, c, r, p);
				slaves[i] = slaveConc;
				thread = new Thread(slaveConc);
				break;
			}

			// insert thread into array
			threads[i] = thread;

			// start thread
			thread.start();
		}

		// waiting for all thread to be finished
		for (int i = 0; i < thread_count; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		long timeEnd = System.currentTimeMillis();
		double runtime = (timeEnd - timeStart)/(double)1000;
		
		switch(method){
		case 1:
			System.out.println("List elements: " + map.size());
			break;
		case 2:
			System.out.println("List elements: " + mapSync.getMapSize());
			break;
		case 3:
			System.out.println("List elements: " + concMap.size());
			break;
		}
		
		// output
		System.out.println("Runtime for " + thread_count + " threads, method " + method + " and " + elements
				+ " elements: " + runtime + " s");

	}

}
