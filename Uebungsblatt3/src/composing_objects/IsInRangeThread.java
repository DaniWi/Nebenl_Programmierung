package composing_objects;

public class IsInRangeThread extends Thread{
	NumberRange range;
	
	public IsInRangeThread(NumberRange range) {
		this.range = range;
	}
	
	public void run(){
		while(!isInterrupted()) {
			Double i = Math.random();
			i = i * 20 - 10;	//values between -10 and 10
			try {
				System.out.println("test value " + i.intValue() + " : IsInRange = " + range.isInRange(i.intValue()));
			
				Thread.sleep((int)(Math.random()*1000));
			} catch (InterruptedException e) {
				System.out.println(e);
				interrupt();
			}
		}
	}

}
