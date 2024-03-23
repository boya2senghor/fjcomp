/*************************************************************************
*   Ce code est g�n�r� et mis en forme par le compilateur FJComp         *
* Auteur du Compilateur: Abdourahmane Senghor  -- boya2senghor@yahoo.fr  *
**************************************************************************/


import java . util . concurrent . ForkJoinPool ;
import java . util . concurrent . RecursiveAction ;
public class Mergesort {
   public int [ ] numbers ;
   public static int [ ] helper ;
   public int number ;
   public int low , high ;
   private int processors ;
   public Mergesort ( int [ ] numbers ) {
      this . numbers = numbers ;
      number = numbers . length ;
      number = numbers . length ;
      high = number - 1 ;
      low = 0 ;
   }
   public Mergesort ( int [ ] numbers , int low , int high ) {
      this . numbers = numbers ;
      number = numbers . length ;
      this . helper = new int [ number ] ;
      this . low = low ;
      this . high = high ;
   }
   public void sort ( ) {
      this . helper = new int [ number ] ;
      mergesort ( low , number - 1 ) ;
   }
   public void mergesort ( int low , int high ) {
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
      mergesortImpl amergesortImpl = new mergesortImpl ( low , high ) ;
      pool . invoke ( amergesortImpl ) ;
   }
   private class mergesortImpl extends RecursiveAction {
      private int low ;
      private int high ;
      private mergesortImpl ( int low , int high ) {
         this . low = low ;
         this . high = high ;
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
         if ( ( high - low ) < 100000 ) {
            mergesort ( low , high ) ;
         }
         else {
            if ( low < high ) {
               int middle = ( low + high ) / 2 ;
               mergesortImpl task1 = null ;
               mergesortImpl task2 = null ;
               task1 = new mergesortImpl ( low , middle ) ;
               task2 = new mergesortImpl ( middle + 1 , high ) ;
               invokeAll ( task1 , task2 ) ;
               merge ( low , middle , high ) ;
            }
         }
      }
      private void mergesort ( int low , int high ) {
         if ( low < high ) {
            int middle = ( low + high ) / 2 ;
            mergesort ( low , middle ) ;
            mergesort ( middle + 1 , high ) ;
            merge ( low , middle , high ) ;
         }
      }
   }
   public void merge ( int low , int middle , int high ) {
      for  (int i = low; i <= high; i++) {
         helper [ i ] = numbers [ i ] ;
      }
      int i = low ;
      int j = middle + 1 ;
      int k = low ;
      while ( i <= middle && j <= high ) {
         if ( helper [ i ] <= helper [ j ] ) {
            numbers [ k ] = helper [ i ] ;
            i ++ ;
         }
         else {
            numbers [ k ] = helper [ j ] ;
            j ++ ;
         }
         k ++ ;
      }
      while ( i <= middle ) {
         numbers [ k ] = helper [ i ] ;
         k ++ ;
         i ++ ;
      }
   }
   public static void main ( String [ ] args ) {
      int N = 128000000 ;
      java . util . Random generator = new java . util . Random ( 1101979 ) ;
      int a [ ] = new int [ N ] ;
      for  (int i = 0; i < N; i++) {
         a [ i ] = generator . nextInt ( 400000000 ) ;
      }
      long startTime = System . currentTimeMillis ( ) ;
      Mergesort mergesort = new Mergesort ( a , 0 , a . length - 1 ) ;
      mergesort . mergesort ( 0 , a . length - 1 ) ;
      long stopTime = System . currentTimeMillis ( ) ;
      long elapsedTime = stopTime - startTime ;
      System . out . println ( "Parallel Mergesort of array of "+a.length+" elements - Elapsed Time: " + elapsedTime+" ms" ) ;
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
 