package futuretask;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 
 * Calculates a part of the Leibniz formula for Pi
 * Range of calculation is passed by the indizes from and to to the start method
 *
 */
public class CalculatePartOfPi {

	private FutureTask<BigDecimal> future;
	
    public void start(Integer from, Integer to) { 
    	future =  new FutureTask<BigDecimal>(new Callable<BigDecimal>() {
    		public BigDecimal call() {
            	BigDecimal result = new BigDecimal("0", MathContext.DECIMAL128);
            	for(int n = from; n <= to; n++)
        		{
        			BigDecimal dividend;
        			//check if odd or even
        			if (n%2 == 0) {
        				dividend = new BigDecimal("1.0", MathContext.DECIMAL128);
        			}
        			else {
        				dividend = new BigDecimal("-1.0", MathContext.DECIMAL128);
        			}
        			//calculate divisor
        			Integer divisor = 2*n+1;		
        			//divide
        			BigDecimal res = dividend.divide(new BigDecimal(divisor.toString()), MathContext.DECIMAL128);
        			//add to result
        			result = result.add(res);
        		}
            	return result;
            }
        });
    	new Thread(future).start(); 
    }
    
    public BigDecimal get() throws InterruptedException, ExecutionException{
        return future.get();
    }
}
