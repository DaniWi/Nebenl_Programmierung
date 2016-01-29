// Monte Carlo simulation of a European call option

package threadpoolexecutor;

import java.util.*;
//-------------------------------
class MCSOriginal {
//-------------------------------

  public static void main(String[] args) {

      double s0=100.0, k=100.0, t=1.0, r=0.05, sig=0.2;

      int m=10000; // jeder der 10 Worker soll 1000 Iterationen der j-Schleife berechnen

      int n=100;

      double dt, nuDt, sigSqrtDt, exprT;
      double eps;
      double st, ct, sum, sum2, se;
      double value;
      Random random = new Random();

      dt        = t/(double)n;
      nuDt      = (r-0.5*sig*sig)*dt;
      sigSqrtDt = sig*Math.sqrt(dt);
      exprT     = Math.exp(-r*t);

      sum = 0.0;
      for (int j = 1; j<=m; j++) { 
          st = s0;
          for (int i = 1; i<=n; i++) {                eps = random.nextGaussian();
              st *= Math.exp(nuDt + sigSqrtDt*eps);
              }
          ct = Math.max(0.0, st - k);
          sum += ct;

          }

      value = sum * exprT / (double)m;
		System.out.println("value="+value);

      } 
  }
