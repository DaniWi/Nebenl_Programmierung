package hash_map;

import java.util.HashMap;

public class HashMapSync {

	private HashMap<Integer, Integer> map;
	
	public HashMapSync(){
		map = new HashMap<Integer, Integer>();
	}
	
	//insert element
	public synchronized Integer put(int key, int value){
		return map.put(key, value);
	}
	
	//remove element
	public synchronized Integer remove(int key){
		return map.remove(key);
	}
	
	//contains element?
	public boolean containsKey(int key){
		return map.containsKey(key);
	}
	
	public int getMapSize(){
		return map.size();
	}
		
}
