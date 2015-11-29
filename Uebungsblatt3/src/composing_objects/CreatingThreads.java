package composing_objects;

public class CreatingThreads {

	public static void main(String[] args) {
		NumberRange range = new NumberRange();
		
		SettingUpperLowerThread[] settingThreads = new SettingUpperLowerThread[3];
		
		for(int i = 0; i < settingThreads.length; i++) {
			settingThreads[i] = new SettingUpperLowerThread(range);
			settingThreads[i].start();
		}
		
		IsInRangeThread testThread = new IsInRangeThread(range);
		testThread.setPriority(Thread.MIN_PRIORITY);
		testThread.start();
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 0; i < settingThreads.length; i++) {
			settingThreads[i].interrupt();
		}
		testThread.interrupt();
	}

}
