import java.util.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.AlphaComposite;
import java.awt.image.*;
import java.io.*;
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
   private int [][][] textures;
   private int texWidth, texHeight;
   public Map(Scanner in){
      width = in.nextInt();
      height = in.nextInt();
      
      matrix = new int[width][height];
      for(int x = 0; x < matrix.length; x ++){
         for(int y = 0; y < matrix[x].length; y ++){
            matrix[x][y] = in.nextInt();
         }
      }
      
      System.out.println("Width: " + width + "\t\t|\tHeight: " + height);
      
      loadTextures();
      texWidth = texHeight = 64;
   }
   private void loadTextures(){
      textures = new int[10][64][64];
      textures[1] = createTextureArray("GrassTexture.jpg");
      textures[2] = createTextureArray("RedTexture.jpg");
      textures[3] = createTextureArray("BlueTexture.jpg");
      textures[4] = createTextureArray("MultiTexture.jpg");
      textures[5] = createTextureArray("RainbowTexture.jpg");
      textures[6] = createTextureArray("BrickTextures.jpg");
      textures[7] = createTextureArray("FractalTexture.jpg");
      textures[8] = createTextureArray("SpaceTexture.jpg");
      textures[9] = createTextureArray("PrisonTexture.jpg");
   }
   public int getPixel(int mapX, int mapY, int x, int y){
      if(isClear(mapX, mapY)){
         return 0;
      }
      if(mapX < 0 || mapX > matrix.length){
         System.out.println("X: " + mapX);
         return 0;
      }
      if(mapY < 0 || mapY > matrix[mapX].length){
         System.out.println("Y: " + mapY);
         return 0;
      }
      int c = getVal(mapX, mapY);
      c = (c > 10)? c - 10: c;
      if(x < 0 || x > textures[c].length || y < 0 || y > textures[c][x].length){
         return 0;
      }
      return textures[c][x][y];
   }
   public int getTexWidth(){
      return texWidth;
   }
   public int getTexHeight(){
      return texHeight;
   }
   private int getVal(int x, int y){
      if(x == 1337 || y == 1337){
         return 0;
      }
      if(x < 0 || x >= getWidth() || y < 0 || y >= getHeight()){
         return 10;
      }
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
         case 6:
            return Color.YELLOW;
         case 7:
            return Color.CYAN;
         case 8:
            return Color.ORANGE;
         case 9:
            return Color.WHITE;
            
      }
      return new Color(120, 120, 120, 120);
   }
   private int[][] createTextureArray(String filename){
      BufferedImage image;
      try{
         image = ImageIO.read(new File("images/" + filename));
      } catch(Exception e){
         System.out.println("cant open image " + filename);
         return new int[0][0];
      }
      final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
      final int width = image.getWidth();
      final int height = image.getHeight();
      final boolean hasAlphaChannel = image.getAlphaRaster() != null;

      int[][] result = new int[height][width];
      final int pixelLength = 3;
      for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
         int argb = 0;
         //argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
         argb += ((int) pixels[pixel + 0] & 0xff); // blue
         argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
         argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
         result[row][col] = argb;
         col++;
         if (col == width) {
            col = 0;
            row++;
         }
      }
      return result;
   }
}