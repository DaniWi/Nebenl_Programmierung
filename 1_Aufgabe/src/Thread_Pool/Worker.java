package Thread_Pool;
import java.math.BigDecimal;
import java.math.MathContext;

public class Worker extends Thread{
	private int start;
	private int stop;
	private BigDecimal result = new BigDecimal("0", MathContext.DECIMAL128);
	
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getStop() {
		return stop;
	}
	public void setStop(int stop) {
		this.stop = stop;
	}
	public BigDecimal getResult() {
		return result;
	}
	
	public void setResult(BigDecimal result) {
		this.result = result;
	}
	public void run(){
		for(int n = start; n <= stop; n++)
		{
			BigDecimal dividend;
			if(n%2 == 0)
				dividend = new BigDecimal("1.0", MathContext.DECIMAL128);
			else
				dividend = new BigDecimal("-1.0", MathContext.DECIMAL128);
			Integer divisor = 2*n+1;			
			BigDecimal res = dividend.divide(new BigDecimal(divisor.toString()), MathContext.DECIMAL128);
			result = result.add(res);
		}
	}
	
}