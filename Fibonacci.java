/*************************************************************************
�   FJCOMP Previous version 0.0  - Date: Fevrier 2013*
*   FJCOMP Version 1.0  - updated : Avril 2023     *
*   Ce code est genere et mis en forme par le compilateur FJComp         *
* Auteur du Compilateur: Abdourahmane Senghor  -- boya2senghor@yahoo.fr  *
**************************************************************************/


package fjcomp ;
import java . util . concurrent . ForkJoinPool ;
import java . util . concurrent . RecursiveAction ;
public class Fibonacci {
   public long fibonacci ( int n ) {
      String nbthreadsStr = System . getProperty ( "fjcomp.threads" ) ;
      int numthreads = Runtime.getRuntime().availableProcessors() ;
      try {
         numthreads = Integer . parseInt ( nbthreadsStr ) ;
         if ( numthreads == 0 ) {
            System . out . println ( "La valeur de fjcomp.threads doit etre differente de zero" ) ;
            System . exit ( 1 ) ;
         }
      }
      catch ( Exception ex ) {
         if ( nbthreadsStr == null ) ;
         else {
            System . out . println ( "La valeur fr fjcomp.threads doit etre un entier" ) ;
            System . exit ( 1 ) ;
         }
      }
      ForkJoinPool pool = new ForkJoinPool ( numthreads ) ;
      fibonacciImpl afibonacciImpl = new fibonacciImpl ( n ) ;
      pool . invoke ( afibonacciImpl ) ;
      return afibonacciImpl . result ;
   }
   private class fibonacciImpl extends RecursiveAction {
      private int n ;
      private long result ;
      private fibonacciImpl ( int n ) {
         this . n = n ;
      }
      protected void compute ( ) {
         int MAX_DEPTH ;
         String maxdepthStr = System . getProperty ( "fjcomp.maxdepth" ) ;
         try {
            MAX_DEPTH = Integer . parseInt ( maxdepthStr ) ;
            if ( MAX_DEPTH == 0 ) {
               System . out . println ( "La valeur de fjcomp.maxdepth doit etre differente de zero" ) ;
               System . exit ( 1 ) ;
            }
         }
         catch ( Exception ex ) {
            if ( maxdepthStr == null ) ;
            else {
               System . out . println ( "La valeur  fjcomp.maxdepth doit etre un entier" ) ;
               System . exit ( 1 ) ;
            }
         }
         if ( n < 40 ) {
            result = fibonacci ( n ) ;
         }
         else {
            if ( n == 0 ) {
               result = 0 ;
            }
            if ( n == 1 ) {
               result = 1 ;
            }
            long x , y ;
            fibonacciImpl task1 = null ;
            fibonacciImpl task2 = null ;
            task1 = new fibonacciImpl ( n - 1 ) ;
            task2 = new fibonacciImpl ( n - 2 ) ;
            invokeAll ( task1 , task2 ) ;
            x = task1 . result ;
            y = task2 . result ;
            result = x + y ;
         }
      }
      private long fibonacci ( int n ) {
         if ( n == 0 ) {
            return 0 ;
         }
         if ( n == 1 ) {
            return 1 ;
         }
         long x , y ;
         x = fibonacci ( n - 1 ) ;
         y = fibonacci ( n - 2 ) ;
         return x + y ;
      }
   }
   public static void main ( String args [ ] ) {
      long startTime = System . currentTimeMillis ( ) ;
      final int n = 47 ;
      Fibonacci fib = new Fibonacci ( ) ;
      long resultat = fib . fibonacci ( n ) ;
      long stopTime = System . currentTimeMillis ( ) ;
      long elapsedTime = stopTime - startTime ;
      System . out . println ( "Fibonacci de " + n + " est de : " + resultat + " Temps d'ex\u00c3\u00a9cution: " + elapsedTime + " ms" ) ;
   }
}
 