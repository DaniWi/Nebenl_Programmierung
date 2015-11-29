package composing_objects;

public class SettingUpperLowerThread extends Thread{
	NumberRange range;
	
	public SettingUpperLowerThread(NumberRange range) {
		this.range = range;
	}

	public void run(){
		while(!isInterrupted()) {
			double i = Math.random();
			if(i < 0.5){
				//setting lower value
				Double value = Math.random();
				value = value * 20 - 10;
				try{
					range.setLower(value.intValue());
					System.out.println("set lower value to " + value.intValue());
				} catch (IllegalArgumentException e) {
					System.out.println(e);
				}
			} else {
				//setting upper value
				Double value = Math.random();
				value = value * 20 - 10;
				try{
					range.setUpper(value.intValue());
					System.out.println("set upper value to " + value.intValue());
				} catch (IllegalArgumentException e) {
					System.out.println(e);
				} catch (InterruptedException e) {
					System.out.println(e);
					interrupt();
				}
			}
			
			try {
				Thread.sleep((int)(Math.random()*2000));
			} catch (InterruptedException e) {
				System.out.println(e);
				interrupt();
			}
		}
	}
}
