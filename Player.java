import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.awt.AlphaComposite;
import java.awt.image.*;
import java.util.*;
/****************************************
*Player is the class the the gamer controls.
*It extends the Entity class, and knows its direction and speed, as well as any changes in direction or speed.

*@Dhanvant, Yash, Keegan
*@1.0.0
*****************************************/
public class Player extends Entity {
   private double dX, dY, dR, dX2, dY2, speed, dirX, dirY, planeX, planeY;  //change in x, change in y, change in direction, max speed
   private double FOV = Math.PI / 2;
   private double[] distances;
   private Map m;
   private int yInc, xInc;
   /****************************************
*Creates a player with starting position (x,y), sets its health value, gives it a direction in radians, gives it a weapon
*and sets a map up, while setting its speed. It also sets up the faction that the player is in, Starfleet.

*@param x X-position
*@param y Y-position
*@param health Health
*@param dir Direction
*@param w Weapon
*@param s Speed
*@param map Map
*****************************************/
   public Player(double x, double y, double health, double dir, Weapon w, double s, Map map){
      super(x, y, health, dir, w, Faction.STARFLEET);
      speed = s;
      m = map;
      distances = new double[800];
      dirX = Math.sin(myDir);
      dirY = Math.cos(myDir);
      planeX = 0;
      planeY = 1;
      yInc = 2;
      xInc = 2;
   }
   /****************************************
*Returns the players Field of View

*@return FOV
*****************************************/
   public double getFOV(){
      return FOV;
   }
   /****************************************
*Checks to ensure that the player character isn't moving into any walls.

*****************************************/
   protected void step(){
   //check for collisions with map
      if(m.isClear((int)(myX + dX + dX2), (int)(myY + dY + dY2))){
         myX += dX;
         myX += dX2;
         myY += dY;
         myY += dY2;
      } else {
         if(m.isClear((int)(myX + dX + dX2), (int)myY)){
            myX += dX;
            myX += dX2;
         } else if(m.isClear((int)myX, (int)(myY + dY + dY2))){
            myY += dY;
            myY += dY2;
         }
      }
      myDir += dR;
      double oldDirX = dirX;
      dirX = dirX * Math.cos(-dR) - dirY * Math.sin(-dR);
      dirY = oldDirX * Math.sin(-dR) + dirY * Math.cos(-dR);
      double oldPlaneX = planeX;
      planeX = planeX * Math.cos(-dR) - planeY * Math.sin(-dR);
      planeY = oldPlaneX * Math.sin(-dR) + planeY * Math.cos(-dR);
      while(myDir >= (2*Math.PI)){
         myDir -= (2*Math.PI);
      }
      while(myDir <= 0){
         myDir += (2*Math.PI);
      }
      //stay in bounds
      if(myX <= 0.01)
         myX = 0.01;
      if(myY <= 0.01)
         myY = 0.01;
      if(myY >= m.getHeight() - 0.01)
         myY = m.getHeight() - 0.01;
      if(myX >= m.getWidth() - 0.01)
         myX = m.getWidth() - 0.01;
         
   }
      /****************************************
*Stops all player movement

*****************************************/
   public void stopMovement(){
      dX = 0;
      dY = 0;
      dR = 0;
   }
      /****************************************
*Stops the player from turning
*****************************************/
   public void stopTurn(){
      dR = 0;
   }
      /****************************************
*Moves the player forward

*****************************************/
   public void forward(){
      
      dX = Math.sin(myDir) * speed;
      dY = Math.cos(myDir) * speed;
   }
   public void stopForward(){
      dX  = 0;
      dY  = 0;
   }
      /****************************************
*Moves the player Back

*****************************************/
   public void back(){
   
      dX = -1 * (Math.sin(myDir) * speed);
      dY = -1 * (Math.cos(myDir) * speed);
   }
   public void stopBack(){
   
      dX = 0;
      dY = 0;
   }
      /****************************************
 *Moves the player left

*****************************************/
   public void left(){
      dX2 = Math.sin(myDir + (Math.PI / 2)) * speed;
      dY2 = Math.cos(myDir + (Math.PI / 2)) * speed;
   }
   public void stopLeft(){
      dX2 = 0;
      dY2 = 0;
   }
      /****************************************
*Moves the player right

*****************************************/
   public void right(){
      dX2 = Math.sin(myDir - (Math.PI / 2)) * speed;
      dY2 = Math.cos(myDir - (Math.PI / 2)) * speed;
   }
   public void stopRight(){
      dX2 = 0;
      dY2 = 0;
   }
      /****************************************
*Turns the player Left

*@param amount The degree at which you are turned right
*****************************************/
   public void tl(double amount){
      dR = (speed / 4) * amount;
   }
      /****************************************
*Turns the player Right

*@param amount The degree to which the player is turned
*****************************************/
   public void tr(double amount){
      dR = -1 * (speed / 4) * amount;
   }
   public void turnAbs(double a){
      myDir += a;
      double oldDirX = dirX;
      dirX = dirX * Math.cos(-a) - dirY * Math.sin(-a);
      dirY = oldDirX * Math.sin(-a) + dirY * Math.cos(-a);
      double oldPlaneX = planeX;
      planeX = planeX * Math.cos(-a) - planeY * Math.sin(-a);
      planeY = oldPlaneX * Math.sin(-a) + planeY * Math.cos(-a);
      while(myDir >= (2*Math.PI)){
         myDir -= (2*Math.PI);
      }
      while(myDir <= 0){
         myDir += (2*Math.PI);
      }

   }
/****************************************
*Gets the distance between the locations
*@param loc The location you wish to find the distance to
*@return distance[]
*****************************************/
   public double getDist(int loc){
      return distances[loc];
   }
   
   public double getDirX(){
      return dirX;
   }
   public double getDirY(){
      return dirY;
   }
   public double getPlaneX(){
      return planeX;
   }
   public double getPlaneY(){
      return planeY;
   }
      /****************************************
*Renders the graphics of the area that you, the palyer character are in

*@param g Graphics of Map
*****************************************/
   public int newRender(Graphics g, double fps){
      if(fps > 35){
         yInc --;
         xInc --;
      }
      if(fps < 20){
         yInc ++;
      }
      if(fps < 10){
         xInc ++;
      }
      if(yInc > (3 * xInc)){
         yInc --;
         xInc ++;
      }
      if(yInc >= 450){
         yInc = 449;
      } else if(yInc <= 1){
         yInc = 1;
      }
      if(xInc >= 800){
         xInc = 799;
      } else if(xInc <= 1){
         xInc = 1;
      }

      //draw ceiling/sky box
      g.setColor(Color.WHITE.darker());
      g.fillRect(0, 0, 800, 225);
      
      //draw floor
      g.setColor(Color.BLACK.brighter());
      g.fillRect(0, 225, 800, 225);
      
      //draw walls
         for(int x = 0; x < 800; x += xInc){
            //calculate ray position and direction
            double cameraX = 2 * (x - 0) / 800.0 - 1; //x-coordinate in camera space
            double rayPosX = myX;
            double rayPosY = myY;
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
               if(mapX >= m.getWidth() || mapY >= m.getHeight() || mapY < 0 || mapX < 0){
                  mapX = 1337;
                  mapY = 1337;
                  break;
               }
               
            }
            if(mapX >= m.getWidth() || mapY >= m.getHeight() || mapY < 0 || mapX < 0){
               mapX = 1337;
               mapY = 1337;
            }
            
            
            if(side == 0){ perpWallDist = (mapX - rayPosX + (1 - stepX) / 2) / rayDirX;}
            else if(side == 1){ perpWallDist = (mapY - rayPosY + (1 - stepY) / 2) / rayDirY;}
            
            
            //store the distance
            for(int i = 0; i < xInc; i ++){
               if((x + i) < 800){
                  distances[x + i] = perpWallDist;
                  if(mapX == 1337 || mapY == 1337){
                     distances[x + i] = 1337;
                  }
               }
            }
            
            int lineHeight = (int)(450 / perpWallDist);
            
            int drawStart = ((-1*lineHeight) / 2) + 225;
            if(drawStart < 0){
               drawStart = 0;
            }
            if(m.isHalf(mapX, mapY)){
               drawStart = 225;
            }
            int drawEnd = (lineHeight / 2) + 225;
            if(drawEnd > 450){
               drawEnd = 450;
            }
            
            double wallX;
            if(side == 0){wallX = rayPosY + (perpWallDist * rayDirY);}
            else {wallX = rayPosX + (perpWallDist * rayDirX);}
            
            wallX -= (int)wallX;
               
            int texWidth = m.getTexWidth();
            
            int texX = (int)(wallX * (double)texWidth);
            if(side == 0 && rayDirX > 0){texX = texWidth - texX - 1;}
            if(side == 1 && rayDirY < 0){texX = texWidth - texX - 1;}
            //System.out.println("start: " + drawStart + "\t\t| end: " + drawEnd);
            
            for(int y = drawStart; y < drawEnd; y+= yInc)
            {
               int d = y * 256 - 450 * 128 + lineHeight * 128;  //256 and 128 factors to avoid floats
               int texY = ((d * m.getTexHeight()) / lineHeight) / 256;
               /*int c1 = m.getPixel(mapX, mapY, texX, texY);
               int c2 = m.getPixel(mapX, mapY, texX, texY + 1);
               int c3 = m.getPixel(mapX, mapY, texX, texY + 2);
               int c4 = m.getPixel(mapX, mapY, texX, texY + 3);
               
               int red = (((c1 & 0xff0000) >> 16) + ((c2 & 0xff0000) >> 16) + ((c3 & 0xff0000) >> 16) + ((c4 & 0xff0000) >> 16)) / 4;
               int green = (((c1 & 0xff00) >> 8) + ((c2 & 0xff00) >> 8) + ((c3 & 0xff00) >> 8) + ((c4 & 0xff00) >> 8)) / 4;
               int blue = ((c1 & 0xff) + (c2 & 0xff) + (c3 & 0xff) + (c4 & 0xff)) / 4;
               
               int color = 0xff000000 + (red << 16) + (green << 8) + blue;*/
               int color = m.getPixel(mapX, mapY, texX, texY);
               //make color darker for y-sides: R, G and B byte each divided through two with a "shift" and an "and"
               if(side == 1) color = (color >> 1) & 8355711;
               g.setColor(new Color(color));
               //System.out.println(color);
               g.fillRect(x - (xInc / 2), y-(yInc / 2), xInc, yInc);
            }
            /*
            g.setColor(m.getColor(mapX, mapY));
            g.drawLine(x, 225 - lineHeight / 2, x, 225 + lineHeight / 2);
            */
         }
      return yInc;
   }
}
