import javax.swing.JPanel;
import java.awt.*;
import java.awt.AlphaComposite;
import java.awt.image.*;
import java.io.*;

public class Scene {
   private Save save;
   private Map map;
   private NPC npc;
   public Scene(Map m, Save s, NPC n){
      map = m;
      save = s;
      npc = n;
   }
   public void drawBackground(Graphics g){
     
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
   public void save(String filename){
      try {
         save.save(new BufferedWriter(new OutputStreamWriter(new FileOutputStream("save/" + filename + ".wp"), "utf-8")));
         
         npc.save(new BufferedWriter(new OutputStreamWriter(new FileOutputStream("npc/" + filename + ".npc"), "utf-8")));
      } catch(Exception e){System.out.println("could not save");}
   }
}