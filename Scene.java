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
   public Save getSave(){
      return save;
   }
   public void left(){
      save.getPlayer().left();
   }
   public void right(){
      save.getPlayer().right();
   }
   public void shoot(Graphics g){
      npc.shoot(save.getPlayer().getWeapon());
   }
   public void back(){
      save.getPlayer().back();
   }
   public void turnRight(double arg){
      save.getPlayer().tr(arg);
   }
   public void turnLeft(double arg){
      save.getPlayer().tl(arg);
   }
   public void stopMovement(){
      save.getPlayer().stopMovement();
   }
   public void render(Graphics bg, Graphics enemyG, Graphics playerG){
   //draw building
      save.render(bg);
   //draw NPCs
      npc.render(enemyG, save.getPlayer());
   
      playerG.setColor(new Color(255, 153, 0));
      playerG.fillRect(0, 400, 50, 50);
      
      //clear the compass
      playerG.setColor(Color.BLACK);
      playerG.fillRect(0, 375, 75, 75);
      
      //draw the map on the compass
      double w = 50.0 / map.getWidth();
      double h = 50.0 / map.getHeight();
      for(int x = 0; x < map.getWidth(); x++){
         for(int y = 0; y < map.getHeight(); y ++){
            if(map.isVisible(x, y)){
               playerG.setColor(map.getColor(x, y));
               playerG.fillRect((int)(x * w), (int)((y * h) + 400), (int)w, (int)h);
            }
         }
      }
      //draw the player on compass
      
      int startX = (int)(save.getPlayer().getX() * w);
      int startY = 400 + (int)(save.getPlayer().getY() * h);
      int x,y;
      
      playerG.setColor(Color.ORANGE); 
      
      
       x = startX + (int)(Math.sin(save.getPlayer().getDir() + (Math.PI / 4)) * 15);
       y = startY + (int)(Math.cos(save.getPlayer().getDir() + (Math.PI / 4)) * 15);
      playerG.drawLine(startX, startY, x, y);
       
       x = startX + (int)(Math.sin(save.getPlayer().getDir() - (Math.PI / 4)) * 15);
       y = startY + (int)(Math.cos(save.getPlayer().getDir() - (Math.PI / 4)) * 15);
      playerG.drawLine(startX, startY, x, y);
       
       x = startX + (int)(Math.sin(save.getPlayer().getDir()) * 15);
       y = startY + (int)(Math.cos(save.getPlayer().getDir()) * 15);
      
        
            
      playerG.setColor(new Color(51, 5, 18));
      playerG.drawLine(startX, startY, x, y);
      
      //write dir at top of screen
      playerG.setColor(Color.BLACK);
      playerG.fillRect(0, 0, 400, 45);
      playerG.setColor(Color.GREEN);
      playerG.setFont(new Font("Arial", Font.BOLD, 20));
      playerG.drawString("DIR: " + save.getPlayer().getDir(), 15, 30);
      
   }
   public void start(){
      save.start();
   }
   public void stopTurn(){
      save.getPlayer().stopTurn();
   }
   public void save(String filename){
      try {
         save.save(new BufferedWriter(new OutputStreamWriter(new FileOutputStream("save/" + filename + ".wp"), "utf-8")));
         
         npc.save(new BufferedWriter(new OutputStreamWriter(new FileOutputStream("npc/" + filename + ".npc"), "utf-8")));
      } catch(Exception e){System.out.println("could not save");}
   }
}