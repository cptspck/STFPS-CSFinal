import javax.swing.JPanel;
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
   public void render(Graphics g){
      save.render(g, npc);
      g.setColor(new Color(255, 153, 0));
      g.fillRect(0, 400, 50, 50);
      
      double w = 50.0 / map.getWidth();
      double h = 50.0 / map.getHeight();
      for(int x = 0; x < map.getWidth(); x++){
         for(int y = 0; y < map.getHeight(); y ++){
            if(map.isVisible(x, y)){
               System.out.println("vis X:" + x + " Y: " + y);
               System.out.println("Width: " + 50 / map.getWidth());
               System.out.println("Width: " + 50 / map.getHeight());
               g.setColor(map.getColor(x, y));
               g.fillRect((int)(x * w), (int)((y * h) + 400), (int)w, (int)h);
            }
         }
      }
      g.setColor(Color.ORANGE);
      for(int i = 0; i <= 800; i ++){
         double angle = (save.getPlayer().getDir() - (Math.PI / 1)) + ((((Math.PI / 2) * i)/800) / 2);
         int x = (int)(save.getPlayer().getX() * w) + (int)(Math.sin(angle) * 15);
         int y = 400 + (int)(save.getPlayer().getY() * h) + (int)(Math.cos(angle) * 15);
         g.drawLine((int)(save.getPlayer().getX() * w), 400 + (int)(save.getPlayer().getY() * h), x, y);
      }
      g.setColor(new Color(51, 5, 18));
      int x = (int)(save.getPlayer().getX() * w) + (int)(Math.sin(save.getPlayer().getDir()) * 5);
      int y = 400 + (int)(save.getPlayer().getY() * h) + (int)(Math.cos(save.getPlayer().getDir()) * 5);
      g.drawLine((int)(save.getPlayer().getX() * w), 400 + (int)(save.getPlayer().getY() * h), x, y);
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