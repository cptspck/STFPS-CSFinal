import java.util.*;
import java.io.*;

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
         threads[i].start();
      }
      dists = new double[npc.length];
   }
   public void save(Writer out) throws Exception{
      String output = "";
      output += npc.length + " ";
      for(int i = 0; i < npc.length; i ++){
         output += npc[i].getX() + " ";
         output += npc[i].getY() + " ";
         output += npc[i].getHealth() + " ";
         output += npc[i].getDir() + " ";
         output += npc[i].getFOV() + " ";
         output += npc[i].getWeapon().getSave() + " ";
         output += npc[i].getPath().getSave() + " ";
      }
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
   public void render(Graphics g, Player player){
      //housekeeping
      manage();
      
      //get info about the player
      double x = player.getX();
      double y = player.getY();
      double dir = player.getDir();
      
      double dirX = Math.sin(dir);
      double dirY = Math.cos(dir);
      
      double planeX = 0;
      double planeY = 1;
      
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
            
            for(int stripe = drawStartX; stripe < drawEndX; stripe++) {
               //the conditions in the if are:
               //1) it's in front of camera plane so you don't see things behind you
               //2) it's on the screen (left)
               //3) it's on the screen (right)
               //4) ZBuffer, with perpendicular distance
               if(stripe == 400){
                  canShoot = false;
               }
               if(transformY > 0 && stripe > 0 && stripe < 800 && transformY < player.getDist(stripe)){
                  if(stripe == 400){
                     canShoot = true;
                  }
                  //for(int ya = drawStartY; ya < drawEndY; ya++){ //for every pixel of the current stripe
                  g.setColor(color);
                  g.drawLine(stripe, drawStartY, stripe, drawEndY); 
                 
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