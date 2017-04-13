import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.awt.AlphaComposite;
import java.awt.image.*;
import java.util.*;

public class Player extends Entity {
   private double dX, dY, dR, speed;  //change in x, change in y, change in direction, max speed
   private double FOV = Math.PI / 2;
   private Map m;
   public Player(double x, double y, double health, double dir, Weapon w, double s, Map map){
      super(x, y, health, dir, w, Faction.STARFLEET);
      speed = s;
      m = map;
   }
   protected void step(){
      if(m.isClear((int)(myX + dX), (int)(myY + dY))){
         myX += dX;
         myY += dY;
      } else {
         if(m.isClear((int)(myX + dX), (int)myY)){
            myX += dX;
         } else if(m.isClear((int)myX, (int)(myY + dY))){
            myY += dY;
         }
      }
      myDir += dR;
      while(myDir <= 0){
         myDir += 2 * Math.PI;
      }
      if(myX <= 0.01)
         myX = 0.01;
      if(myY <= 0.01)
         myY = 0.01;
      if(myY >= m.getHeight() - 0.01)
         myY = m.getHeight() - 0.01;
      if(myX >= m.getWidth() - 0.01)
         myX = m.getWidth() - 0.01;
         
   }
   public void stopMovement(){
      dX = 0;
      dY = 0;
      dR = 0;
   }
   public void forward(){
      
      dX = Math.sin(myDir) * speed;
      dY = Math.cos(myDir) * speed;
   }
   public void back(){
   
      dX = -1 * (Math.sin(myDir) * speed);
      dY = -1 * (Math.cos(myDir) * speed);
   }
   public void left(){
      dX = Math.sin(myDir + (Math.PI / 2)) * speed;
      dY = Math.cos(myDir + (Math.PI / 2)) * speed;
   }
   public void right(){
      dX = Math.sin(myDir - (Math.PI / 2)) * speed;
      dY = Math.cos(myDir - (Math.PI / 2)) * speed;
   }
   public void tl(){
      dR = (speed / 4);
   }
   public void tr(){
      dR = -1 * (speed / 4);
   }
   public void newRender(Graphics g){
      //draw ceiling/sky box
      g.setColor(Color.WHITE.darker());
      g.fillRect(0, 0, 800, 225);
      
      //draw floor
      g.setColor(Color.BLACK.brighter());
      g.fillRect(0, 225, 800, 225);
      
      //draw walls
      double planeX = 0;
      double planeY = 0.66;
         for(int x = 0; x <= 800; x ++){
            //calculate ray position and direction
            double cameraX = 2 * (x - 0) / 800.0 - 1; //x-coordinate in camera space
            double rayPosX = myX;
            double rayPosY = myY;
            double dirX = Math.sin(myDir);
            double dirY = Math.cos(myDir);
            double rayDirX = dirX + planeX * cameraX;
            double rayDirY = dirY + planeY * cameraX;
            
            int mapX = (int)rayPosX;
            int mapY = (int)rayPosY;
            
            double sideDistX, sideDistY;
            
            double deltaDistX = Math.sqrt(1 + Math.pow(rayDirY, 2) / Math.pow(rayDirX, 2));
            double deltaDistY = Math.sqrt(1 + Math.pow(rayDirX, 2) / Math.pow(rayDirY, 2));
            
            double perpWallDist = 1000;
            
            int stepX, stepY;
            
            int hits = 0;
            int side = 7;
            
            if (rayDirX < 0)
            {
              stepX = -1;
              sideDistX = (rayPosX - mapX) * deltaDistX;
            }
            else
            {
              stepX = 1;
              sideDistX = (mapX + 1.0 - rayPosX) * deltaDistX;
            }
            if (rayDirY < 0)
            {
              stepY = -1;
              sideDistY = (rayPosY - mapY) * deltaDistY;
            }
            else
            {
              stepY = 1;
              sideDistY = (mapY + 1.0 - rayPosY) * deltaDistY;
            }
            
            while(hits == 0){
               if(sideDistX < sideDistY){
                  sideDistX += deltaDistX;
                  mapX += stepX;
                  side = 0;
               } else {
                  sideDistY += deltaDistY;
                  mapY += stepY;
                  side = 1;
               }
               if(m.isVisible(mapX, mapY)){hits = 1;}
               if(mapX > m.getWidth() || mapY > m.getHeight() || mapY < 0 || mapX < 0){break;}
               
            }
            
            if(side == 0){ perpWallDist = (mapX - rayPosX + (1 - stepX) / 2) / rayDirX;}
            else if(side == 1){ perpWallDist = (mapY - rayPosY + (1 - stepY) / 2) / rayDirY;}
            
            int lineHeight = (int)(800 / perpWallDist);
            
            g.setColor(m.getColor(mapX, mapY));
            g.drawLine(x, 225 - lineHeight / 2, x, 225 + lineHeight / 2);
            
         }
   }
}