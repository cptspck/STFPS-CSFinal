import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.awt.AlphaComposite;
import java.awt.image.*;
import java.util.*;

public class Player extends Entity {
   private double dX, dY, speed;
   private double FOV = Math.PI / 2;
   private Map m;
   public Player(double x, double y, double health, double dir, Weapon w, double s, Map map){
      super(x, y, health, dir, w, Faction.STARFLEET);
      speed = s;
      m = map;
   }
   protected void step(){
      if(myX <= 0.01)
         myX = 0.01;
      if(myY <= 0.01)
         myY = 0.01;
      if(myY >= m.getHeight() - 0.01)
         myY = m.getHeight() - 0.01;
      if(myX >= m.getWidth() - 0.01)
         myX = m.getWidth() - 0.01;
         
   }
   public void forward(){
      myX += Math.sin(myDir - Math.PI) * speed;
      myY += Math.cos(myDir - Math.PI) * speed;
   }
   public void back(){
      myX += Math.sin(myDir) * speed;
      myY += Math.cos(myDir) * speed;
   }
   public void left(){
      myX += Math.sin(myDir - (Math.PI / 2)) * speed;
      myY += Math.cos(myDir - (Math.PI / 2)) * speed;
   }
   public void right(){
      myX += Math.sin(myDir + (Math.PI / 2)) * speed;
      myY += Math.cos(myDir + (Math.PI / 2)) * speed;
   }
   public void tl(){
      myDir -= speed;
      while(myDir <= 0){
         myDir += 2 * Math.PI;
      }
   }
   public void tr(){
      myDir += speed;
      while(myDir >= 2 * Math.PI){
         myDir -= 2* Math.PI;
      }
   }
   public void render(Graphics g){
      //draw ceiling/sky box
      g.setColor(Color.WHITE.darker());
      g.fillRect(0, 0, 800, 225);
      
      //draw floor
      g.setColor(Color.BLACK.brighter());
      g.fillRect(0, 225, 800, 225);
      
      //draw walls
      for(int i = 0; i <= 800; i ++){
       //figure out the angle to project the ray at
         double angle = (myDir - (Math.PI / 1)) + (((FOV * i)/800) / 2);
                   /*  given that x and y represent this box:
               (x,y) => M------N  <= (x + (< 1), y)
                        |      |
                        |  10  |
                        |      |
      (x, y + (< 1)) => O------P  <= (x + (<1), y + (< 1))
                         
                       the ray crosses that box if it crosses any of the line segments.
                       also, as the ray goes forever, we only have to check two of the line segments
                       
                           to see if it crosses a line segment, we check the bounds of the segment, and see 
                           if the ray's value there is within the bounds
                           
                     for the horizontal piece (MN):
                     
                        if RAYx at My/Ny is between (inclusive) Mx and Nx, then the ray crosses MN
                        
                        if(x <= calcRayX(y) < x+1)
                           true
                  
                     for the vertical piece (MO):
                        same deal, but calc the Y instead of the X
                      
                        if(y <= calcRayY(x) < y + 1)
                           true  
                  
                  */
         int[] coords = new int[m.getWidth()];
         for(int x = 0; x < m.getWidth(); x ++){  
            int rayY = (int)((Math.tan(angle) * (x - myX)) + myY);
            if(rayY < m.getHeight() && rayY >= 0){
               coords[x] = (m.isVisible(x, rayY)) ? rayY : 999999;
            } else {
               coords[x] = 99999;
            }
         }
         int min = 0;
         double minDistance = 99999;
         for(int j = 0; j < coords.length; j ++){
            double jDist = Math.sqrt(Math.pow(j - myX, 2) + Math.pow(coords[j] - myY, 2));
            double minDist = Math.sqrt(Math.pow(min - myX, 2) + Math.pow(coords[min] - myY, 2));
            if(jDist <= minDist){
               min = j;
               minDistance = minDist;
            }
         }
         int xLineHeight = 999999999;
         Color xColor = Color.BLACK;
         if(coords[min] <= m.getHeight() && minDistance < 1000){
            xLineHeight = Math.abs((int)(450 / (minDistance * 2 * Math.cos(angle))));
            xColor = m.getColor(min, coords[min]).darker();
         }
         //now do it all over again for the vertical pieces
         coords = new int[m.getHeight()];
         for(int y = 0; y < m.getHeight(); y ++){  
            int rayX = (int)((y - myY)/(Math.tan(angle)) + myX);
            if(rayX < m.getHeight() && rayX >= 0){
               coords[y] = (m.isVisible(rayX, y)) ? rayX : 999999;
            } else {
               coords[y] = 99999;
            }
         }
         min = 0;
         minDistance = 99999;
         for(int j = 0; j < coords.length; j ++){
            double jDist = Math.sqrt(Math.pow(j - myY, 2) + Math.pow(coords[j] - myX, 2));
            double minDist = Math.sqrt(Math.pow(min - myY, 2) + Math.pow(coords[min] - myX, 2));
            if(jDist <= minDist){
               min = j;
               minDistance = minDist;
            }
         }
         int yLineHeight = 999999999;
         Color yColor = Color.WHITE;
         if(coords[min] <= m.getWidth() && minDistance < 1000){
            yLineHeight = Math.abs((int)(450 / (minDistance * 2 * Math.cos(angle))));
            yColor = (m.getColor(coords[min], min));
         }
         if(yLineHeight < 100000 || xLineHeight < 100000){
            if(xLineHeight > 100000){
               xLineHeight = -5;
            }
            if(yLineHeight > 100000){
               yLineHeight = -5;
            }
            g.setColor((yLineHeight > xLineHeight)? yColor:xColor);
            int lineHeight = (yLineHeight > xLineHeight)? yLineHeight : xLineHeight;
            g.drawLine(i, 225 - lineHeight, i, 225 + lineHeight);
         }
      }
      g.setColor(Color.ORANGE);
      g.drawString("X: " + myX + ", Y: " + myY + " |dir: " + myDir, 5, 30);
   }
}