/*
Keegan Lanzillotta
Version 0.1
April 7, 2017
*/
import java.util.*;
/*
FORMAT FOR MAP FILES:
<width> <height>

               (<width> wide)
                     |
                    /\
 __________________/  \_________________________
/<value> <value> <value> <value> <value> <value>\
 <value> <value> <value> <value> <value> <value> |
 <value> <value> <value> <value> <value> <value> |
 <value> <value> <value> <value> <value> <value>  \
 <value> <value> <value> <value> <value> <value>   >-(<height> tall)
 <value> <value> <value> <value> <value> <value>  /
 <value> <value> <value> <value> <value> <value> |
 <value> <value> <value> <value> <value> <value> |
 
 
FORMAT FOR <value>:
two digit number, first digit is texture for half height objects, second is for full height
if there is a value for half hight, the full height value SHOULD be 0 (nothing), but will be ignored no matter what

e.g.:

5 8
01 01 01 01 01             |---|
01 00 00 00 02             |   :
01 00 00 00 02        \    |   :
01 10 10 00 02      ===>   |xx :
01 00 00 00 01        /    |   |
01 00 20 20 03             | vv?
01 00 00 00 03             |   ?
01 01 00 01 03             |- -?
*/
public class Map {
   private int width;
   private int height;
   private int[][] matrix;
   public Map(Scanner in){
      width = in.nextInt();
      height = in.nextInt();
      
      matrix = new int[width][height];
      for(int i = 0; i < matrix.length; i ++){
         for(int j = 0; j < matrix[i].length; j ++){
            matrix[i][j] = in.nextInt();
         }
      }
   }
}