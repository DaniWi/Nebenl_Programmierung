package hash_map;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Slave implements Runnable {

	private HashMap<Integer, Integer> map;
	private HashMapSync mapSync;
	private ConcurrentHashMap<Integer, Integer> concMap;
	private int contains;
	private int remove;
	private int put;
	private int method = 0;
	
	//hash map constructor
	public Slave(HashMap<Integer, Integer> map, int contains, int remove, int put){
		this(contains, remove, put);
		this.map      = map;
		this.method   = 1;
	}
	
	//synchronized hash map constructor
	public Slave(HashMapSync map, int contains, int remove, int put){
		this(contains, remove, put);
		this.mapSync  = map;
		this.method   = 2;
	}
	
	//concurrent hash map constructor
	public Slave(ConcurrentHashMap<Integer, Integer> map, int contains, int remove, int put){
		this(contains, remove, put);
		this.concMap  = map;
		this.method   = 3;
	}
	
	//general constructor
	private Slave(int contains, int remove, int put){
		this.contains = contains;
		this.remove   = remove;
		this.put      = put;
	}
	
	@Override
	public void run() {

		System.out.println("Thread " + Thread.currentThread().getName() + " started to put " + this.put + " elements");
		
		//put
		for(int i = 0; i < put; i++){
			int random = (int) (10000001 * Math.random());
			switch(this.method){
			case 1:
				map.put(random, random);
				break;
			case 2:
				mapSync.put(random, random);
				break;
			case 3:
				concMap.put(random, random);
				break;
			}

		}
		
		System.out.println("Thread " + Thread.currentThread().getName() + " started to read " + this.contains + " elements");
		
		//contains
		for(int i = 0; i < contains; i++){
			int random = (int) (10000001 * Math.random());
			switch(this.method){
			case 1:
				map.containsKey(random);
				break;
			case 2:
				mapSync.containsKey(random);
				break;
			case 3:
				concMap.containsKey(random);
				break;
			}

		}
		
		System.out.println("Thread " + Thread.currentThread().getName() + " started to remove " + + this.remove + " elements");
		
		//remove
		for(int i = 0; i < remove; i++){
			int random = (int) (10000001 * Math.random());
			switch(this.method){
			case 1:
				map.remove(random);
				break;
			case 2:
				mapSync.remove(random);
				break;
			case 3:
				concMap.remove(random);
				break;
			}

		}
			
	}
	
}
