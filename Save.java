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
   private String w, m;
   private Player player;
   private Map map;
   private Thread thread;
   public Save(Scanner in){
      x = in.nextDouble();
      y = in.nextDouble();
      d = in.nextDouble();
      h = in.nextDouble();
      m = in.next();
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
      try{
         map = new Map(new Scanner(new File("map/" + m + ".map")));
      } catch(FileNotFoundException e){
         System.out.println("Player has bad Map");
         System.exit(0);
         return;
      }
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
   public void shoot(Graphics g){
      g.setColor(new Color(119, 231, 239));
      g.drawLine(400, 225, 630, 330);
   }
   public void save(Writer out) throws Exception{
   //make a sring to save
      String output = "";
      output += x + " ";
      output += y + " ";
      output += d + " ";
      output += h + " ";
      output += m + " ";
      output += w + " ";
      output += s + " ";
      
   //save it
      out.write(output);
   }
   public void render(Graphics g){
      player.newRender(g);
   }
}