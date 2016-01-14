package linkedblockingdeque;

public class Job {

	private int work;
	private int price;
	private int id;
	
	//constructor
	public Job(int work, int price, int id){
		this.work = work;
		this.price = price;
		this.id = id;
	}
	
	//getters
	public int getId() {
		return id;
	}
	
	public int getWork() {
		return work;
	}
	public int getPrice() {
		return price;
	}
	
	//setters
	public void setWork(int work) {
		this.work = work;
	}
	public void setPrice(int price) {
		this.price = price;
	}

}
