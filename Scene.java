import javax.swing.*;
import java.awt.*;
import java.awt.AlphaComposite;
import java.awt.image.*;
import java.io.*;
import java.util.*;
/*
   *.sc files format:
<.map file name>
<.save file name>
<.npc file name>
<next .sc file name>
*/
public class Scene {
   private Save save;
   private Map map;
   private NPC npc;
   private int frames, yInc;
   private double fps, startTime;
   private String next;
   public Scene(){} //for nextScene() to compile
   public Scene(Scanner in){
      try{
         map = new Map(new Scanner( new File("map/" + in.next() + ".map")));
      }catch(FileNotFoundException e){
         System.out.println("Scene has bad Map");
         System.exit(0);
         return;
      }
      try{
         save = new Save(new Scanner( new File("save/" + in.next() + ".save")), map);
      }catch(FileNotFoundException e){
         System.out.println("Scene has bad Save");
         System.exit(0);
         return;
      }
      try{
         npc = new NPC(new Scanner( new File("npc/" + in.next() + ".npc")));
      }catch(FileNotFoundException e){
         System.out.println("Scene has bad NPC");
         System.exit(0);
         return;
      }
      next = in.next();
      startTime = System.currentTimeMillis();
      frames = 0;
      fps = 0;
   }
   public Scene nextScene(){
      Scene scene;
      try{
         scene = new Scene(new Scanner(new File("nextScene/" + next + ".sc")));
      } catch(FileNotFoundException e){
         System.out.println("Scene has bad next Scene");
         System.exit(0);
         return new Scene();
      }
      System.out.println("new planeX: " + scene.getPlaneX() + "\t| new planeY: " + scene.getPlaneY());
      return scene;
   }
   public double getPlaneX(){
      return save.getPlayer().getPlaneX();
   }
   public double getPlaneY(){
      return save.getPlayer().getPlaneY();
   }
   public void forward(){
      save.getPlayer().forward();
   }
   public Save getSave(){
      return save;
   }
   public NPC getNPC(){
      return npc;
   }
   public void left(){
      save.getPlayer().left();
   }
   public void right(){
      save.getPlayer().right();
   }
   public void stopForward(){
      save.getPlayer().stopForward();
   }
   public void stopBack(){
      save.getPlayer().stopBack();
   }
   public void stopLeft(){
      save.getPlayer().stopLeft();
   }
   public void stopRight(){
      save.getPlayer().stopRight();
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
   public void turnAbs(double arg){
      save.getPlayer().turnAbs(arg);
   }
   public void stopMovement(){
      save.getPlayer().stopMovement();
   }
   public boolean shouldLevel(){
      return save.getPlayer().shouldLevel();
   }
   public void render(Graphics bg, Graphics enemyG, Graphics playerG){
   
   //draw building
      yInc = save.getPlayer().yInc(fps);
      if(save.getPlayer().hasChanged()){
         save.render(bg);
      }
      frames++;
   //draw NPCs
      npc.render(enemyG, save.getPlayer(), yInc);
   
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
      //draw NPCs on compass
      playerG.setColor(new Color(255, 80, 10));
      Enemy[] enemies = npc.getNPCs();
      for(int i = 0; i < enemies.length; i ++){
         if(enemies[i].isAlive()){
            int x = (int)(enemies[i].getX() * w);
            int y = 400 + (int)(enemies[i].getY() * h);
            playerG.fillOval(x - 2, y - 2, 4, 4);
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
      playerG.fillRect(0, 0, 100, 80);
      playerG.setColor(Color.GREEN);
      playerG.setFont(new Font("Arial", Font.BOLD, 20));
      fps = frames / ((System.currentTimeMillis() - startTime) / 1000.0);
      playerG.drawString("FPS: " + (int)fps, 15, 30);
      playerG.drawString("vRes: " + (450 / yInc), 5, 70);
      
      if((System.currentTimeMillis() - startTime) > 500){
         frames = 1;
         startTime = System.currentTimeMillis();
      }
      
   }
   public void start(){
      save.start();
      npc.start();
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