/*
Keegan Lanzillotta
Version 0.1
April 7, 2017
*/
import javax.swing.JPanel;
import java.awt.*;
import java.awt.AlphaComposite;
import java.awt.event.*;
import java.awt.image.*;

public class Panel extends JPanel {
   private static int width;
   private static int height;
   //for background
   private BufferedImage bgImage;
   private Graphics bgBuffer;
   //for enemies and other entities
   private BufferedImage enemyImage;
   private Graphics enemyBuffer;
   //for player weapons, hands, tools, etc
   private BufferedImage playerImage;
   private Graphics playerBuffer;
   
   public Panel(int w, int h){
      width = w;
      height = h;
      
      bgImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      bgBuffer = bgImage.getGraphics();
      
      enemyImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      enemyBuffer = enemyImage.getGraphics();
      
      playerImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      playerBuffer = playerImage.getGraphics();
   }
}