/*
Keegan Lanzillotta
Version 0.1
April 7, 2017
*/
import javax.swing.JPanel;
import java.awt.*;
import java.awt.AlphaComposite;
import java.awt.image.*;

public class Loader{
   private Graphics g;
   public Loader(Graphics graphics){
      g = graphics;
      start();
   }
   private void start() {
      g.setColor(Color.BLACK);
      g.fillRect(0, 0, 700, 500);
      
      g.setColor(new Color(255, 153, 0));
      g.setFont(new Font("Arial", Font.BOLD, 60));
      g.drawString("STFPS: Dorvan V", 100, 120);
      
      g.setColor(new Color(153, 153, 204));
      g.fillRect(250, 250, 200, 70);
      
      g.setColor(Color.BLACK);
      g.setFont(new Font("Arial", Font.BOLD, 40));
      g.drawString("PLAY", 300, 300);
   } 
}