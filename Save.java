import java.util.*;
import java.io.*;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.awt.AlphaComposite;
import java.awt.image.*;
/*
SAVE FILE FORMAT:

<player's x>
<player's y>
<player's dir>
<player's health>
<player's map (<name>.map file)>
<player's weapon (<name>.wp file)>
<player's speed>
*/
public class Save {
   private double x, y, d, h, s;
   private String w;
   private Player player;
   private Map map;
   private Thread thread;
   public Save(Scanner in, Map m){
      x = in.nextDouble();
      y = in.nextDouble();
      d = in.nextDouble();
      h = in.nextDouble();
      w = in.next();
      s = in.nextDouble();
      
      Weapon weapon;
      try{
         weapon = new Weapon(new Scanner(new File("weapons/" + w + ".wp")), w);
      } catch(FileNotFoundException e){
         System.out.println("Player has bad Weapon");
         System.exit(0);
         return;
      }
      map = m;
      player = new Player(x, y, h, d, weapon, s, map);
   }
   public Map getMap(){
      return map;
   }
   public Player getPlayer(){
      return player;
   }
   public void start(){
      thread = new Thread(player);
      thread.start();
      System.out.println("player thread started");
   }
   public void stop(){
      thread.stop();
   }
   public void shoot(Graphics g, int offset){
      int startX = ((offset * 230) / 15) + 400;
      int startY = ((offset * 75) / 15) + 225;
      int endX = (((offset - 5) * 230) / 15) + 400;
      int endY = (((offset - 5) * 75) / 15) + 225;
      
      startX = (startX >= 630) ? 630 : startX;
      startY = (startY >= 330) ? 330 : startY;
      endX = (endX <= 400) ? 400 : endX;
      endY = (endY <= 225) ? 225 : endY;
      
      g.setColor(new Color(119, 231, 239));
      g.drawLine(startX, startY, endX, endY);
   }
   public void save(Writer out) throws Exception{
   //make a string to save
      String output = "";
      output += x + " ";
      output += y + " ";
      output += d + " ";
      output += h + " ";
      output += w + " ";
      output += s + " ";
      
   //save it
      out.write(output);
   }
   public void render(Graphics g){
      player.newRender(g);
   }
}