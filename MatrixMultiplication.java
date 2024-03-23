/*************************************************************************
*   Ce code est g�n�r� et mis en forme par le compilateur FJComp         *
* Auteur du Compilateur: Abdourahmane Senghor  -- boya2senghor@yahoo.fr  *
**************************************************************************/


package fjcomp ;
import java . util . ArrayList ;
import java . util . concurrent . ForkJoinPool ;
import java . util . concurrent . RecursiveAction ;
import java . util . Vector ;
public class MatrixMultiplication {
   static int LEAF_SIZE = 512 ;
   public static int [ ] [ ] ijkAlgorithmVector ( Vector < Vector < Integer > > A , Vector < Vector < Integer > > B ) {
      int n = A . size ( ) ;
      int [ ] [ ] C = new int [ n ] [ n ] ;
      for  (int i = 0; i < n; i++) {
         for  (int j = 0; j < n; j++) {
            for  (int k = 0; k < n; k++) {
               C [ i ] [ j ] += A . get ( i ) . get ( k ) * B . get ( k ) . get ( j ) ;
            }
         }
      }
      return C ;
   }
   public static int [ ] [ ] ijkAlgorithm ( ArrayList < ArrayList < Integer > > A , ArrayList < ArrayList < Integer > > B ) {
      int n = A . size ( ) ;
      int [ ] [ ] C = new int [ n ] [ n ] ;
      for  (int i = 0; i < n; i++) {
         for  (int k = 0; k < n; k++) {
            for  (int j = 0; j < n; j++) {
               C [ i ] [ j ] += A . get ( i ) . get ( k ) * B . get ( k ) . get ( j ) ;
            }
         }
      }
      return C ;
   }
   public static int [ ] [ ] ikjAlgorithm ( ArrayList < ArrayList < Integer > > A , ArrayList < ArrayList < Integer > > B ) {
      int n = A . size ( ) ;
      int [ ] [ ] C = new int [ n ] [ n ] ;
      for  (int i = 0; i < n; i++) {
         for  (int k = 0; k < n; k++) {
            for  (int j = 0; j < n; j++) {
               C [ i ] [ j ] += A . get ( i ) . get ( k ) * B . get ( k ) . get ( j ) ;
            }
         }
      }
      return C ;
   }
   public static int [ ] [ ] ikjAlgorithm ( int [ ] [ ] A , int [ ] [ ] B ) {
      int n = A . length ;
      int [ ] [ ] C = new int [ n ] [ n ] ;
      for  (int i = 0; i < n; i++) {
         for  (int k = 0; k < n; k++) {
            for  (int j = 0; j < n; j++) {
               C [ i ] [ j ] += A [ i ] [ k ] * B [ k ] [ j ] ;
            }
         }
      }
      return C ;
   }
   private static int [ ] [ ] add ( int [ ] [ ] A , int [ ] [ ] B ) {
      int n = A . length ;
      int [ ] [ ] C = new int [ n ] [ n ] ;
      for  (int i = 0; i < n; i++) {
         for  (int j = 0; j < n; j++) {
            C [ i ] [ j ] = A [ i ] [ j ] + B [ i ] [ j ] ;
         }
      }
      return C ;
   }
   private static int [ ] [ ] subtract ( int [ ] [ ] A , int [ ] [ ] B ) {
      int n = A . length ;
      int [ ] [ ] C = new int [ n ] [ n ] ;
      for  (int i = 0; i < n; i++) {
         for  (int j = 0; j < n; j++) {
            C [ i ] [ j ] = A [ i ] [ j ] - B [ i ] [ j ] ;
         }
      }
      return C ;
   }
   private static int nextPowerOfTwo ( int n ) {
      int log2 = ( int ) Math . ceil ( Math . log ( n ) / Math . log ( 2 ) ) ;
      return ( int ) Math . pow ( 2 , log2 ) ;
   }
   public static int [ ] [ ] strassen ( ArrayList < ArrayList < Integer > > A , ArrayList < ArrayList < Integer > > B ) {
      int n = A . size ( ) ;
      int m = nextPowerOfTwo ( n ) ;
      int [ ] [ ] APrep = new int [ m ] [ m ] ;
      int [ ] [ ] BPrep = new int [ m ] [ m ] ;
      for  (int i = 0; i < n; i++) {
         for  (int j = 0; j < n; j++) {
            APrep [ i ] [ j ] = A . get ( i ) . get ( j ) ;
            BPrep [ i ] [ j ] = B . get ( i ) . get ( j ) ;
         }
      }
      int [ ] [ ] CPrep = strassenR ( APrep , BPrep ) ;
      int [ ] [ ] C = new int [ n ] [ n ] ;
      for  (int i = 0; i < n; i++) {
         for  (int j = 0; j < n; j++) {
            C [ i ] [ j ] = CPrep [ i ] [ j ] ;
         }
      }
      return C ;
   }
   private static int [ ] [ ] strassenR ( int [ ] [ ] A , int [ ] [ ] B ) {
      String nbthreadsStr = System . getProperty ( "fjcomp.threads" ) ;
      int numthreads = Runtime . getRuntime ( ) . availableProcessors ( ) ;
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
      strassenRImpl astrassenRImpl = new strassenRImpl ( 0 , A , B ) ;
      pool . invoke ( astrassenRImpl ) ;
      return astrassenRImpl . result ;
   }
   private static class strassenRImpl extends RecursiveAction {
      private int maxdepth ;
      private int [ ] [ ] A ;
      private int [ ] [ ] B ;
      private int [ ] [ ] result ;
      private strassenRImpl ( int maxdepth , int [ ] [ ] A , int [ ] [ ] B ) {
         this . maxdepth = maxdepth ;
         this . A = A ;
         this . B = B ;
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
         if ( maxdepth >= 1 ) {
            result = strassenR ( A , B ) ;
         }
         else {
            int n = A . length ;
            if ( n <= LEAF_SIZE ) {
               result = ikjAlgorithm ( A , B ) ;
            }
            else {
               int newSize = n / 2 ;
               int [ ] [ ] a11 = new int [ newSize ] [ newSize ] ;
               int [ ] [ ] a12 = new int [ newSize ] [ newSize ] ;
               int [ ] [ ] a21 = new int [ newSize ] [ newSize ] ;
               int [ ] [ ] a22 = new int [ newSize ] [ newSize ] ;
               int [ ] [ ] b11 = new int [ newSize ] [ newSize ] ;
               int [ ] [ ] b12 = new int [ newSize ] [ newSize ] ;
               int [ ] [ ] b21 = new int [ newSize ] [ newSize ] ;
               int [ ] [ ] b22 = new int [ newSize ] [ newSize ] ;
               int [ ] [ ] aResult = new int [ newSize ] [ newSize ] ;
               int [ ] [ ] bResult = new int [ newSize ] [ newSize ] ;
               for  (int i = 0; i < newSize; i++) {
                  for  (int j = 0; j < newSize; j++) {
                     a11 [ i ] [ j ] = A [ i ] [ j ] ;
                     a12 [ i ] [ j ] = A [ i ] [ j + newSize ] ;
                     a21 [ i ] [ j ] = A [ i + newSize ] [ j ] ;
                     a22 [ i ] [ j ] = A [ i + newSize ] [ j + newSize ] ;
                     b11 [ i ] [ j ] = B [ i ] [ j ] ;
                     b12 [ i ] [ j ] = B [ i ] [ j + newSize ] ;
                     b21 [ i ] [ j ] = B [ i + newSize ] [ j ] ;
                     b22 [ i ] [ j ] = B [ i + newSize ] [ j + newSize ] ;
                  }
               }
               strassenRImpl task1 = null ;
               strassenRImpl task2 = null ;
               strassenRImpl task3 = null ;
               strassenRImpl task4 = null ;
               strassenRImpl task5 = null ;
               strassenRImpl task6 = null ;
               strassenRImpl task7 = null ;
               aResult = add ( a11 , a22 ) ;
               bResult = add ( b11 , b22 ) ;
               int [ ] [ ] p1 ;
               task1 = new strassenRImpl ( maxdepth + 1 , aResult , bResult ) ;
               aResult = add ( a21 , a22 ) ;
               int [ ] [ ] p2 ;
               task2 = new strassenRImpl ( maxdepth + 1 , aResult , b11 ) ;
               bResult = subtract ( b12 , b22 ) ;
               int [ ] [ ] p3 ;
               task3 = new strassenRImpl ( maxdepth + 1 , a11 , bResult ) ;
               bResult = subtract ( b21 , b11 ) ;
               int [ ] [ ] p4 ;
               task4 = new strassenRImpl ( maxdepth + 1 , a22 , bResult ) ;
               aResult = add ( a11 , a12 ) ;
               int [ ] [ ] p5 ;
               task5 = new strassenRImpl ( maxdepth + 1 , aResult , b22 ) ;
               aResult = subtract ( a21 , a11 ) ;
               bResult = add ( b11 , b12 ) ;
               int [ ] [ ] p6 ;
               task6 = new strassenRImpl ( maxdepth + 1 , aResult , bResult ) ;
               aResult = subtract ( a12 , a22 ) ;
               bResult = add ( b21 , b22 ) ;
               int [ ] [ ] p7 ;
               task7 = new strassenRImpl ( maxdepth + 1 , aResult , bResult ) ;
               invokeAll ( task1 , task2 , task3 , task4 , task5 , task6 , task7 ) ;
               p1 = task1 . result ;
               p2 = task2 . result ;
               p3 = task3 . result ;
               p4 = task4 . result ;
               p5 = task5 . result ;
               p6 = task6 . result ;
               p7 = task7 . result ;
               int [ ] [ ] c12 = add ( p3 , p5 ) ;
               int [ ] [ ] c21 = add ( p2 , p4 ) ;
               aResult = add ( p1 , p4 ) ;
               bResult = add ( aResult , p7 ) ;
               int [ ] [ ] c11 = subtract ( bResult , p5 ) ;
               aResult = add ( p1 , p3 ) ;
               bResult = add ( aResult , p6 ) ;
               int [ ] [ ] c22 = subtract ( bResult , p2 ) ;
               int [ ] [ ] C = new int [ n ] [ n ] ;
               for  (int i = 0; i < newSize; i++) {
                  for  (int j = 0; j < newSize; j++) {
                     C [ i ] [ j ] = c11 [ i ] [ j ] ;
                     C [ i ] [ j + newSize ] = c12 [ i ] [ j ] ;
                     C [ i + newSize ] [ j ] = c21 [ i ] [ j ] ;
                     C [ i + newSize ] [ j + newSize ] = c22 [ i ] [ j ] ;
                  }
               }
               result = C ;
            }
         }
      }
      private int [ ] [ ] strassenR ( int [ ] [ ] A , int [ ] [ ] B ) {
         int n = A . length ;
         if ( n <= LEAF_SIZE ) {
            return ikjAlgorithm ( A , B ) ;
         }
         else {
            int newSize = n / 2 ;
            int [ ] [ ] a11 = new int [ newSize ] [ newSize ] ;
            int [ ] [ ] a12 = new int [ newSize ] [ newSize ] ;
            int [ ] [ ] a21 = new int [ newSize ] [ newSize ] ;
            int [ ] [ ] a22 = new int [ newSize ] [ newSize ] ;
            int [ ] [ ] b11 = new int [ newSize ] [ newSize ] ;
            int [ ] [ ] b12 = new int [ newSize ] [ newSize ] ;
            int [ ] [ ] b21 = new int [ newSize ] [ newSize ] ;
            int [ ] [ ] b22 = new int [ newSize ] [ newSize ] ;
            int [ ] [ ] aResult = new int [ newSize ] [ newSize ] ;
            int [ ] [ ] bResult = new int [ newSize ] [ newSize ] ;
            for  (int i = 0; i < newSize; i++) {
               for  (int j = 0; j < newSize; j++) {
                  a11 [ i ] [ j ] = A [ i ] [ j ] ;
                  a12 [ i ] [ j ] = A [ i ] [ j + newSize ] ;
                  a21 [ i ] [ j ] = A [ i + newSize ] [ j ] ;
                  a22 [ i ] [ j ] = A [ i + newSize ] [ j + newSize ] ;
                  b11 [ i ] [ j ] = B [ i ] [ j ] ;
                  b12 [ i ] [ j ] = B [ i ] [ j + newSize ] ;
                  b21 [ i ] [ j ] = B [ i + newSize ] [ j ] ;
                  b22 [ i ] [ j ] = B [ i + newSize ] [ j + newSize ] ;
               }
            }
            aResult = add ( a11 , a22 ) ;
            bResult = add ( b11 , b22 ) ;
            int [ ] [ ] p1 ;
            p1 = strassenR ( aResult , bResult ) ;
            aResult = add ( a21 , a22 ) ;
            int [ ] [ ] p2 ;
            p2 = strassenR ( aResult , b11 ) ;
            bResult = subtract ( b12 , b22 ) ;
            int [ ] [ ] p3 ;
            p3 = strassenR ( a11 , bResult ) ;
            bResult = subtract ( b21 , b11 ) ;
            int [ ] [ ] p4 ;
            p4 = strassenR ( a22 , bResult ) ;
            aResult = add ( a11 , a12 ) ;
            int [ ] [ ] p5 ;
            p5 = strassenR ( aResult , b22 ) ;
            aResult = subtract ( a21 , a11 ) ;
            bResult = add ( b11 , b12 ) ;
            int [ ] [ ] p6 ;
            p6 = strassenR ( aResult , bResult ) ;
            aResult = subtract ( a12 , a22 ) ;
            bResult = add ( b21 , b22 ) ;
            int [ ] [ ] p7 ;
            p7 = strassenR ( aResult , bResult ) ;
            int [ ] [ ] c12 = add ( p3 , p5 ) ;
            int [ ] [ ] c21 = add ( p2 , p4 ) ;
            aResult = add ( p1 , p4 ) ;
            bResult = add ( aResult , p7 ) ;
            int [ ] [ ] c11 = subtract ( bResult , p5 ) ;
            aResult = add ( p1 , p3 ) ;
            bResult = add ( aResult , p6 ) ;
            int [ ] [ ] c22 = subtract ( bResult , p2 ) ;
            int [ ] [ ] C = new int [ n ] [ n ] ;
            for  (int i = 0; i < newSize; i++) {
               for  (int j = 0; j < newSize; j++) {
                  C [ i ] [ j ] = c11 [ i ] [ j ] ;
                  C [ i ] [ j + newSize ] = c12 [ i ] [ j ] ;
                  C [ i + newSize ] [ j ] = c21 [ i ] [ j ] ;
                  C [ i + newSize ] [ j + newSize ] = c22 [ i ] [ j ] ;
               }
            }
            return C ;
         }
      }
   }
   public static void main ( String args [ ] ) {
      int N = 2048 ;
      int [ ] [ ] result_ = new int [ N ] [ N ] ;
      int [ ] [ ] a = new int [ N ] [ N ] ;
      for (int i=0;i<N;i++) for (int j=0;j<N;j++) {
         a [ i ] [ j ] = 2 ;
      }
      long startTime = System . currentTimeMillis ( ) ;
      result_ = MatrixMultiplication . strassenR ( a , a ) ;
      long stopTime = System . currentTimeMillis ( ) ;
      long elapsedTime = stopTime - startTime ;
      System . out . println ( "Strassen Matrix Multiplication Elapsed Time: " + elapsedTime+" ms" ) ;
     /// print ( result_ ) ;
   }
   static public void print ( int [ ] [ ] C ) {
      for  (int i=0;i<C.length;i++) {
         for (int j=0;j<C.length;j++) {
            System . out . print ( C [ i ] [ j ] + " " ) ;
         }
         System . out . println ( ) ;
      }
   }
}
 