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
   
   private Scene scene;  
    
   public Panel(int w, int h){
      width = w;
      height = h;
      
      bgImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      bgBuffer = bgImage.getGraphics();
      
      enemyImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      enemyBuffer = enemyImage.getGraphics();
      
      playerImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      playerBuffer = playerImage.getGraphics();
      
      addKeyListener(new Key());
      setFocusable(true);
      
      Loader.load_init(bgBuffer, this);
   }
   private class Key implements KeyListener {
      public void keyPressed(KeyEvent e){
         if(scene != null){         
            if(e.getKeyCode() == KeyEvent.VK_W){
               scene.forward();
            }         
            if(e.getKeyCode() == KeyEvent.VK_A){
               scene.left();
            }         
            if(e.getKeyCode() == KeyEvent.VK_S){
               scene.back();
            }         
            if(e.getKeyCode() == KeyEvent.VK_D){
               scene.right();
            }
         }         
         if(e.getKeyCode() == KeyEvent.VK_Q && e.getModifiers() == KeyEvent.CTRL_MASK){
            System.exit(0);
         }
      }
      public void keyReleased(KeyEvent e){}
      public void keyTyped(KeyEvent e){}
   }
   public void paintComponent(Graphics g)
   {
      g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), null);
      g.drawImage(enemyImage, 0, 0, getWidth(), getHeight(), null);
      g.drawImage(playerImage, 0, 0, getWidth(), getHeight(), null);
   }
}