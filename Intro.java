import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.AlphaComposite;
import java.awt.image.*;
import java.io.*;

public class Intro{
   private static final int SCREEN_W = 800;
   private static final int SCREEN_H = 450;
   public static void playIntro(Graphics g, Panel p, String file){
      boolean running = true;
      
      int[][] texture = createTextureArray(file);
      int[][] distTable = new int[SCREEN_H][SCREEN_W];
      int[][] angleTable = new int[SCREEN_H][SCREEN_W];
      
      for(int y = 0; y < SCREEN_H; y++){
         for(int x = 0; x < SCREEN_W; x++){
            int angle, dist;
            double ratio = 32.0;
            dist = (int)(ratio * texture.length / (Math.sqrt((x - SCREEN_W / 2.0) * (x - SCREEN_W / 2.0) + (y - SCREEN_H / 2.0) * (y - SCREEN_H / 2.0)) + 1)) % texture.length;
            angle = Math.abs((int)(0.5*texture[0].length * Math.atan2(y-SCREEN_H / 2.0, x - SCREEN_W / 2.0) / Math.PI));
            
            distTable[y][x] = dist;
            angleTable[y][x] = angle;
         }
      }
      int animation = 1;
      while(running){
         
         int shiftX = (int)(texture[0].length * animation);
         int shiftY = (int)(texture.length * 0.25 * animation);
         for(int y = 0; y < SCREEN_H; y++){
            for(int x = 0; x < SCREEN_W; x++){
               int color = texture[(int)Math.abs(distTable[y][x] + shiftX) % texture[0].length][(int)Math.abs(angleTable[y][x] + shiftY) % texture.length];
               //System.out.println("Color: " + color);
               g.setColor(new Color(color));
               g.drawLine(x, y, x, y);
            } 
         }
         animation += 1.5;
         if(animation > 300){
            running = false;
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, SCREEN_W, SCREEN_H);
         }
         if(file.equals("rock.png")){ //second part of intro with rocky tunnel
            g.setColor(Color.BLACK);
            g.fillOval(SCREEN_W / 2 - (int)Math.pow(animation, 2) / 180, SCREEN_H / 2 - (int)Math.pow(animation, 2) / 180, (int)(2 * Math.pow(animation, 2) / 180), (int)(2 * Math.pow(animation, 2) / 180));
         } else { //first part in space
            g.drawImage(new ImageIcon("images/planet.png").getImage(), SCREEN_W / 2 - (int)Math.pow(animation, 2) / 550, SCREEN_H / 2 - (int)Math.pow(animation, 2) / 550, (int)(2 * Math.pow(animation, 2) / 550), (int)(2 * Math.pow(animation, 2) / 550), null);
         }
         p.repaint();
      }
   }
   private static int[][] createTextureArray(String filename){
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