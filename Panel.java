import javax.swing.*;
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
   
   //the scene to run from
   private Scene scene;  
   
   //key listener
   private Key key; 
   
   //thread to run the animation
   private Thread animator;
    
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
   public void loaded(Scene s){
      
      //draw phaser
      playerBuffer.drawImage(new ImageIcon("images/phaser.png").getImage(), 610, 300, 190, 150, null);
      playerBuffer.setColor(new Color(50, 0, 80, 255));
      playerBuffer.fillRect(350, 223, 100, 4);
      playerBuffer.fillRect(398, 175, 4, 100);
      
      scene = s;
      key = new Key();
      addKeyListener(key);
      scene.render(bgBuffer);
      repaint();
      scene.start();
      
      animator = new Thread(new Runnable() {
         public void run(){
            while(true){
               scene.render(bgBuffer);
               repaint();
               try {
                  Thread.sleep(15);
               } catch (Exception e){}
            }
         }
      });
      
      animator.start();
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
            if(e.getKeyCode() == KeyEvent.VK_Q){
               scene.turnLeft();
            }         
            if(e.getKeyCode() == KeyEvent.VK_E){
               scene.turnRight();
            }
         }         
         if(e.getKeyCode() == KeyEvent.VK_Q && e.getModifiers() == KeyEvent.CTRL_MASK){
            System.exit(0);
         }
      }
      public void keyReleased(KeyEvent e){
         if(scene != null)
            scene.stopMovement();
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