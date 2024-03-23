/*************************************************************************
*   Ce code est g�n�r� et mis en forme par le compilateur FJComp         *
* Auteur du Compilateur: Abdourahmane Senghor  -- boya2senghor@yahoo.fr  *
**************************************************************************/


package fjcomp ;
import java . util . concurrent . ForkJoinPool ;
import java . util . concurrent . RecursiveAction ;
public class Quicksort {
   public int a [ ] , left , right ;
   public Quicksort ( int [ ] a , int left , int right ) {
      this . a = a ;
      this . left = left ;
      this . right = right ;
   }
   public void sequentialQuicksort ( int [ ] a , int left , int right ) {
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
      sequentialQuicksortImpl asequentialQuicksortImpl = new sequentialQuicksortImpl ( a , left , right ) ;
      pool . invoke ( asequentialQuicksortImpl ) ;
   }
   private class sequentialQuicksortImpl extends RecursiveAction {
      private int [ ] a ;
      private int left ;
      private int right ;
      private sequentialQuicksortImpl ( int [ ] a , int left , int right ) {
         this . a = a ;
         this . left = left ;
         this . right = right ;
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
         if ( ( right - left ) < 300000 ) {
            sequentialQuicksort ( a , left , right ) ;
         }
         else {
            if ( left < right ) {
               int pivotIndex = partition ( a , left , right ) ;
               sequentialQuicksortImpl task1 = null ;
               sequentialQuicksortImpl task2 = null ;
               if ( left < pivotIndex ) task1 = new sequentialQuicksortImpl ( a , left , pivotIndex ) ;
               if ( pivotIndex + 1 < right ) task2 = new sequentialQuicksortImpl ( a , pivotIndex + 1 , right ) ;
               invokeAll ( task1 , task2 ) ;
            }
         }
      }
      private void sequentialQuicksort ( int [ ] a , int left , int right ) {
         if ( left < right ) {
            int pivotIndex = partition ( a , left , right ) ;
            if ( left < pivotIndex ) sequentialQuicksort ( a , left , pivotIndex ) ;
            if ( pivotIndex + 1 < right ) sequentialQuicksort ( a , pivotIndex + 1 , right ) ;
         }
      }
   }
   public int partition ( int [ ] a , int left , int right ) {
      int pivotValue = a [ middleIndex ( left , right ) ] ;
      -- left ;
      ++ right ;
      while ( true ) {
         do {
            ++ left ;
         }
         while ( a [ left ] < pivotValue ) ;
         do {
            -- right ;
         }
         while ( a [ right ] > pivotValue ) ;
         if ( left < right ) {
            int tmp = a [ left ] ;
            a [ left ] = a [ right ] ;
            a [ right ] = tmp ;
         }
         else {
            return right ;
         }
      }
   }
   private int middleIndex ( int left , int right ) {
      return left + ( right - left ) / 2 ;
   }
   public static void main ( String [ ] args ) {
      final int N = 128000000 ;
      java . util . Random generator = new java . util . Random ( 1101979 ) ;
      int a [ ] = new int [ N ] ;
      for  (int i = 0; i < N; i++) {
         a [ i ] = generator . nextInt ( 400000000 ) ;
      }
      long startTime = System . currentTimeMillis ( ) ;
      Quicksort qs = new Quicksort ( a , 0 , a . length - 1 ) ;
      qs . sequentialQuicksort ( a , 0 , a . length - 1 ) ;
      long stopTime = System . currentTimeMillis ( ) ;
      long elapsedTime = stopTime - startTime ;
      System . out . println ( "Parallel QS execution of array of " + a . length + " elements - Elapsed Time: " + elapsedTime + " ms" ) ;
      testQS ( a ) ;
   }
   public static void testQS ( int numbers [ ] ) {
      for  (int i = 0; i < numbers.length - 1; i++) {
         if ( numbers [ i ] > numbers [ i + 1 ] ) {
            System . out . println ( "faild" ) ;
            System . exit ( 1 ) ;
         }
      }
      System . out . println ( "successfull" ) ;
   }
}
 