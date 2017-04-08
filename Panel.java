/*
Keegan Lanzillotta
Version 0.1
April 7, 2017
*/
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.awt.AlphaComposite;
import java.awt.image.*;

public class Panel extends JPanel {
   private int width;
   private int height;
   //for background
   private BufferedImage bgImage;
   private Graphics bgBuffer;
   //for enemies and other entities
   private BufferedImage enemyImage;
   private Graphics enemyBuffer;
   //for player weapons, hands, tools, etc
   private BufferedImage playerImage;
   private Graphics playerBuffer;
   
   private Loader load;  
    
   public Panel(int w, int h){
      width = w;
      height = h;
      
      bgImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      bgBuffer = bgImage.getGraphics();
      
      enemyImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      enemyBuffer = enemyImage.getGraphics();
      
      playerImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      playerBuffer = playerImage.getGraphics();
      
      load = new Loader(bgBuffer);
      
      addKeyListener(new Key());
      setFocusable(true);
   }
   private class Key implements KeyListener {
      public void keyPressed(KeyEvent e){         
         if(e.getKeyCode() == KeyEvent.VK_Q && e.getModifiers() == InputEvent.CTRL_MASK){
            System.exit(0);
         }
      }
      public void keyReleased(KeyEvent e){         
         if(e.getKeyCode() == KeyEvent.VK_Q){
            System.exit(0);
         }
      }
      public void keyTyped(KeyEvent e){}
   }
   public void paintComponent(Graphics g)
   {
      g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), null);
      g.drawImage(enemyImage, 0, 0, getWidth(), getHeight(), null);
      g.drawImage(playerImage, 0, 0, getWidth(), getHeight(), null);
   }
}