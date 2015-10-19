package Thread_Pool;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Scanner;

public class Master {

	public static void main(String[] args) throws InterruptedException {
		//read in the thread number
		System.out.println("How many threads do you like?");
		Scanner scanner = new Scanner(System.in);
		int threads = scanner.nextInt();
		
		BigDecimal pi = new BigDecimal(Math.PI/4, MathContext.DECIMAL128);
		int position = 0;
		BigDecimal result = new BigDecimal("0", MathContext.DECIMAL128);
		double difference = 1.0;
		
		//start measuring the time
		long timeStart = System.currentTimeMillis();
		//check if precision is arrived
		while(difference > 0.000001){
			//create threads and start them
			Worker[] wor = new Worker[threads];
			for(int i = 0; i < threads; i++){
				wor[i] = new Worker();
				wor[i].setStart(position);
				wor[i].setStop(position+3);
				position = position + 4;
				wor[i].start();
			}
			//wait for all threads to finish caluclation
			for(int i = 0; i < threads; i++){
				wor[i].join();
				result = result.add(wor[i].getResult());
			} 
			BigDecimal temp = new BigDecimal("0", MathContext.DECIMAL128);
			temp = result.subtract(pi);
			temp = temp.abs();
			//calculate difference between original PI/4 and calculated PI/4
			difference = temp.doubleValue();
		}
		//stop time measuring
		long timeEnd = System.currentTimeMillis();
		double timeResult = (timeEnd - timeStart)/(double)1000;
		System.out.println(result);
		System.out.println(pi);
		System.out.println("Laufzeit: " + timeResult + "s");
	}

}
