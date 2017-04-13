import java.util.*;
import java.awt.*;
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
   private int getVal(int x, int y){
      return matrix[x][y];
   }
   public boolean isFull(int x, int y){
      int val = getVal(x, y);
      return val < 10 && val > 0; //full height textures are between 01 - 09, inclusive
   }
   public boolean isHalf(int x, int y){
      int val = getVal(x, y);
      return val >= 10 && val < 20; //half height textures are between 10 and 19, inclusive
   }
   public boolean isClear(int x, int y){
      return getVal(x, y) == 0; //only clear if its a 00
   }
   public boolean isVisible(int x, int y){
      return !isClear(x, y);
   }
   public int getWidth(){
      return width;
   }
   public int getHeight(){
      return height;
   }
   public Color getColor(int x, int y){
      int c = getVal(x, y);
      c = (c > 10)? c - 10: c;
      switch(c){
         case 1:
            return Color.GREEN;
         case 2:
            return Color.RED;
         case 3:
            return Color.BLUE;
         case 4:
            return Color.PINK;
         case 5:
            return Color.MAGENTA;
            
      }
      return Color.GRAY;
   }
}