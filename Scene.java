import javax.swing.*;
import java.awt.*;
import java.awt.AlphaComposite;
import java.awt.image.*;
import java.io.*;

public class Scene {
   private Save save;
   private Map map;
   private NPC npc;
   public Scene(Save s, NPC n){
      save = s;
      npc = n;
      map = save.getMap();
   }
   public void forward(){
      save.getPlayer().forward();
   }
   public void left(){
      save.getPlayer().left();
   }
   public void right(){
      save.getPlayer().right();
   }
   public void back(){
      save.getPlayer().back();
   }
   public void turnRight(){
      save.getPlayer().tr();
   }
   public void turnLeft(){
      save.getPlayer().tl();
   }
   public void stopMovement(){
      save.getPlayer().stopMovement();
   }
   public void render(Graphics g){
      save.render(g, npc);
      g.setColor(new Color(255, 153, 0));
      g.fillRect(0, 400, 50, 50);
      
      //draw the map on the compass
      double w = 50.0 / map.getWidth();
      double h = 50.0 / map.getHeight();
      for(int x = 0; x < map.getWidth(); x++){
         for(int y = 0; y < map.getHeight(); y ++){
            if(map.isVisible(x, y)){
               g.setColor(map.getColor(x, y));
               g.fillRect((int)(x * w), (int)((y * h) + 400), (int)w, (int)h);
            }
         }
      }
      //draw the player on compass
      
      int startX = (int)(save.getPlayer().getX() * w);
      int startY = 400 + (int)(save.getPlayer().getY() * h);
      int x,y;
      
      g.setColor(Color.ORANGE); 
      
      
       x = startX + (int)(Math.sin(save.getPlayer().getDir() + (Math.PI / 4)) * 15);
       y = startY + (int)(Math.cos(save.getPlayer().getDir() + (Math.PI / 4)) * 15);
      g.drawLine(startX, startY, x, y);
       
       x = startX + (int)(Math.sin(save.getPlayer().getDir() - (Math.PI / 4)) * 15);
       y = startY + (int)(Math.cos(save.getPlayer().getDir() - (Math.PI / 4)) * 15);
      g.drawLine(startX, startY, x, y);
       
       x = startX + (int)(Math.sin(save.getPlayer().getDir()) * 15);
       y = startY + (int)(Math.cos(save.getPlayer().getDir()) * 15);
      
        
            
      g.setColor(new Color(51, 5, 18));
      g.drawLine(startX, startY, x, y);
      
   }
   public void start(){
      save.start();
   }
   public void save(String filename){
      try {
         save.save(new BufferedWriter(new OutputStreamWriter(new FileOutputStream("save/" + filename + ".wp"), "utf-8")));
         
         npc.save(new BufferedWriter(new OutputStreamWriter(new FileOutputStream("npc/" + filename + ".npc"), "utf-8")));
      } catch(Exception e){System.out.println("could not save");}
   }
}