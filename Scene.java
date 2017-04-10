import javax.swing.JPanel;
import java.awt.*;
import java.awt.AlphaComposite;
import java.awt.image.*;

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
}