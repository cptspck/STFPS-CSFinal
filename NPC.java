import java.util.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.AlphaComposite;
import java.awt.image.*;
/*
NPC FILE FORMAT:
                  <number of NPCs>
(for each NPC:)   <NPC x> <NPC y> <NPC health> <NPC dir> <NPC FOV> <NPC faction> <NPC weapon (<name>.wp file)> <NPC path (<name>.path file)>
*/

public class NPC{
   private Enemy[] npc;
   private double[] dists;
   private Thread[] threads;
   private int[][][] textures;
   public NPC(Scanner in){
      npc = new Enemy[in.nextInt()];
      threads = new Thread[npc.length];
      for(int i = 0; i < npc.length; i ++){
         double x = in.nextDouble();
         double y = in.nextDouble();
         double h = in.nextDouble();
         double d = in.nextDouble();
         double f = in.nextDouble();
         int faction = in.nextInt();
         String wf = in.next();
         String pf = in.next();
         Weapon w;
         Path p;
         try{
            w = new Weapon(new Scanner(new File("weapons/" + wf + ".wp")), wf);
         } catch (FileNotFoundException e){
            System.out.println("FILE NOT FOUND: weapons/" + wf + ".wp");
            return;
         }
         try{
            p = new Path(new Scanner(new File("path/" + pf + ".path")), pf);
         } catch (FileNotFoundException e){
            System.out.println("FILE NOT FOUND: weapons/" + pf + ".wp");
            return;
         }
         npc[i] = new Enemy(x, y, h, d, f, w, p, faction, npc);
         threads[i] = new Thread(npc[i]);
      }
      dists = new double[npc.length];
      loadTexts();
   }
   private void loadTexts(){
      textures = new int[6][][];
      textures[1] = createTextureArray("npc/dukat.png");
      textures[2] = createTextureArray("npc/maquis.png");
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

      int[][] result = new int[height + 1][width + 1];
      final int pixelLength = 4;
      for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
         int argb = 0;
         argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
         argb += ((int) pixels[pixel + 1] & 0xff); // blue
         argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
         argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
         result[row][col] = argb;
         col++;
         if (col == width) {
            col = 0;
            row++;
         }
      }
      return result;
   }
   public Enemy[] getNPCs(){
      return npc;
   }
   //start all the threads once scene is ready
   public void start(){
      for(int i = 0; i < threads.length; i ++){   
         threads[i].start();
      }
   }
   //stop all threads before moving on to next scene
   public void stop(){
      for(int i = 0; i < threads.length; i ++){
         threads[i].stop();
      }
   }
   public void save(PrintWriter out) throws Exception{
      out.println(npc.length + " ");
      for(int i = 0; i < npc.length; i ++){
         out.print(npc[i].getX() + " ");
         out.print(npc[i].getY() + " ");
         out.print(npc[i].getHealth() + " ");
         out.print(npc[i].getDir() + " ");
         out.print(npc[i].getFOV() + " ");
         out.print(npc[i].getFaction() + " ");
         out.print(npc[i].getWeapon().getSave() + " ");
         out.println(npc[i].getPath().getSave() + " ");
      }
      out.close();
   }
   public void shoot(Weapon w){
      for(int i = 0; i < npc.length; i ++){
         npc[i].shot(w, dists[i]);
      }
   }
   //handle background stuff
   private void manage(){
      for(int i = 0; i < npc.length; i++){
         if(!npc[i].isAlive()){
            threads[i].stop();
         }
      }
   }
   public void render(Graphics g, Player player, int xInc, int yInc){
      //housekeeping
      manage();
      
      //get info about the player
      double x = player.getX();
      double y = player.getY();
      double dir = player.getDir();
      
      double dirX = player.getDirX();
      double dirY = player.getDirY();
      
      double planeX = player.getPlaneX();
      double planeY = player.getPlaneY();
      
      double FOV = player.getFOV();
      //iterate through each npc and see if player can see them
      double leftBound = dir - FOV / 2;
      double rightBound = dir + FOV / 2;
      while(rightBound > 2 * Math.PI){
         rightBound -= 2 * Math.PI;
      }
      
      Color color = new Color(255, 80, 10);
      
      for(int i = 0; i < npc.length; i ++){
         if(npc[i].getHealth() > 0){
            double spriteX = npc[i].getX() - x;
            double spriteY = npc[i].getY() - y;
            
            double invDet = 1.0 / (planeX * dirY - dirX * planeY); //required for correct matrix multiplication
            
            double transformX = invDet * (dirY * spriteX - dirX * spriteY);
            double transformY = invDet * (-planeY * spriteX + planeX * spriteY); //this is actually the depth inside the screen, that what Z is in 3D
            
            int spriteScreenX = (int)((800 / 2) * (1 + transformX / transformY));
            
            final double vMove = 0.0;
            final int uDiv = 1;
            final int vDiv = 1;
            
            
            int vMoveScreen = (int)(vMove / transformY);
            
            
            //calculate height of the sprite on screen
            int spriteHeight = Math.abs((int)(450 / (transformY))) / vDiv; //using "transformY" instead of the real distance prevents fisheye
            //calculate lowest and highest pixel to fill in current stripe
            int drawStartY = -spriteHeight / 2 + 450 / 2 + vMoveScreen;
            if(drawStartY < 0) drawStartY = 0;
            int drawEndY = spriteHeight / 2 + 450 / 2 + vMoveScreen;
            if(drawEndY >= 450) drawEndY = 450 - 1;
            
             //calculate width of the sprite
            int spriteWidth = Math.abs( (int)(450 / (transformY))) / uDiv;
            int drawStartX = -spriteWidth / 2 + spriteScreenX;
            if(drawStartX < 0) drawStartX = 0;
            int drawEndX = spriteWidth / 2 + spriteScreenX;
            if(drawEndX >= 800) drawEndX = 800 - 1;
            
            boolean canShoot = false;
            
            for(int stripe = drawStartX; stripe < drawEndX; stripe+= xInc) {
               //the conditions in the if are:
               //1) it's in front of camera plane so you don't see things behind you
               //2) it's on the screen (left)
               //3) it's on the screen (right)
               //4) ZBuffer, with perpendicular distance
               if(stripe == 400){
                  canShoot = false;
               }
               if(transformY > 0 && stripe > 0 && stripe < 800 && transformY < player.getDist(stripe) && npc[i].getFaction() > 0){
                  if(stripe == 400){
                     canShoot = true;
                  }
                  int texX = (int)((256 * (stripe - (-spriteWidth / 2 + spriteScreenX)) * (textures[npc[i].getFaction()][0].length) / spriteWidth) / 256.0);
                  texX = (texX > (textures[npc[i].getFaction()][0].length-1)) ? (textures[npc[i].getFaction()][0].length-1) : texX;
                  for(int ya = drawStartY; ya < drawEndY; ya+=yInc){ //for every pixel of the current stripe
                     int h = 450;
                     int d = (ya * 256) - (h * 128) + (spriteHeight * 128); //256 and 128 factors to avoid floats
                     int texY = ((d * textures[npc[i].getFaction()][0].length) / spriteHeight) / 256;
                     texY = (texY > (textures[npc[i].getFaction()].length-1)) ? (textures[npc[i].getFaction()].length-1) : texY;
                     texY = (texY < 0) ? 0 : texY;
                     texX = (texX < 0) ? 0 : texX;
                     int c = textures[npc[i].getFaction()][texY][texX];
                     if((c & 0x00FFFFFF) != 0){
                        g.setColor(new Color(c));
                        g.fillRect(stripe, ya, xInc, yInc); 
                     }
                  }
               }
            }
            npc[i].setShootability(canShoot);
            dists[i] = transformY;
         }
      }
   }
   private double findAngle(double playX, double playY, Enemy enemy){
      double angle = Math.atan2(playX - enemy.getX(), playY - enemy.getY());
      return angle;
   }
}