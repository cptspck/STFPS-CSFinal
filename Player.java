import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.awt.AlphaComposite;
import java.awt.image.*;
import java.util.*;
/****************************************
*Player is the class the the gamer controls.
*It extends the Entity class, and knows his direction, and speed, as well as any changes in direction or speed.

*@Dhanvant, Yash, Keegan
*@1.0.0
*****************************************/
public class Player extends Entity {
   private double dX, dY, dR, speed;  //change in x, change in y, change in direction, max speed
   private double FOV = Math.PI / 2;
   private double[] distances;
   private Map m;
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
      //never turn back!!
      myDir += dR;
      if(myDir <= 0){
         myDir = 0;
      }
      if(myDir >= Math.PI){
         myDir = Math.PI;
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
      /****************************************
*Moves the player Back

*****************************************/
   public void back(){
   
      dX = -1 * (Math.sin(myDir) * speed);
      dY = -1 * (Math.cos(myDir) * speed);
   }
      /****************************************
 *Moves the player left

*****************************************/
   public void left(){
      dX = Math.sin(myDir + (Math.PI / 2)) * speed;
      dY = Math.cos(myDir + (Math.PI / 2)) * speed;
   }
      /****************************************
*Moves the player right

*****************************************/
   public void right(){
      dX = Math.sin(myDir - (Math.PI / 2)) * speed;
      dY = Math.cos(myDir - (Math.PI / 2)) * speed;
   }
      /****************************************
*Turns the player Right

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
      /****************************************
*Gets the distance between the locations

*@param loc The location you wish to find the distance to
*@return distance[]
*****************************************/
   public double getDist(int loc){
      return distances[loc];
   }
      /****************************************
*Renders the graphics of the area that you, the palyer character are in

*@param g Graphics of Map
*****************************************/
   public void newRender(Graphics g){
      //draw ceiling/sky box
      g.setColor(Color.WHITE.darker());
      g.fillRect(0, 0, 800, 225);
      
      //draw floor
      g.setColor(Color.BLACK.brighter());
      g.fillRect(0, 225, 800, 225);
      
      //draw walls
      double planeX = 0;
      double planeY = 1;
         for(int x = 0; x < 800; x ++){
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
            
            
            //store the distance
            distances[x] = perpWallDist;
            
            int lineHeight = (int)(450 / perpWallDist);
            
            g.setColor(m.getColor(mapX, mapY));
            g.drawLine(x, 225 - lineHeight / 2, x, 225 + lineHeight / 2);
            
         }
   }
}
