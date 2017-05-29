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
   
   //mouse listener
   private Mouse mouse;
   private MouseClick mouseclick;
   
   //thread to run the animation
   private Thread animator;
   
   //screen dimensions:
   private int widthReal, heightReal;
   private Robot robot;
   protected boolean waiting = false;
    
   private int playerShooting = 0;
   public Panel(int w, int h){      
      try{
         robot = new Robot();
      } catch(AWTException e){
         System.exit(1);
      }
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
      
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      widthReal = (int) screenSize.getWidth();
      heightReal = (int) screenSize.getHeight();
   }
   public void loaded(Scene s){
      
      scene = s;
      key = new Key();
      mouse = new Mouse();
      mouseclick = new MouseClick();
      addKeyListener(key);
      addMouseMotionListener(mouse);
      addMouseListener(mouseclick);
      //scene.render(bgBuffer, enemyBuffer, playerBuffer);
      repaint();
      
      animator = new Thread(new Runnable() {
         public void run(){
            while(waiting){}
            BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
            Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
            
            setCursor(blankCursor);
            scene.start();
            while(true){
               for(int x = 0; x < 800; x ++){
                  for(int y = 0; y < 450; y ++){
                     enemyImage.setRGB(x, y, 0);
                  }
               }
               //clear the screen
               for(int x = 0; x < 800; x ++){
                  for(int y = 0; y < 450; y ++){
                     playerImage.setRGB(x, y, 0);
                  }
               }
         
               //draw phaser
               playerBuffer.drawImage(new ImageIcon("images/phaser.png").getImage(), 610, 300, 190, 150, null);
               playerBuffer.setColor(new Color(50, 0, 80, 255));
               playerBuffer.fillRect(350, 223, 100, 4);
               playerBuffer.fillRect(398, 175, 4, 100);
               //draw phaser shot
               if(playerShooting > 0){
                  playerShooting -= 5;
                  scene.getSave().shoot(playerBuffer, playerShooting);
               }
               scene.render(bgBuffer, enemyBuffer, playerBuffer);
               repaint();
               try {
                  Thread.sleep(15);
               } catch (Exception e){}
            }
         }
      });
      
      animator.start();
   }
   public void playIntro(){
      waiting = true;
      key = new Key();
      addKeyListener(key);
      final Panel panel = this;
      waiting = false;
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
      public void keyReleased(KeyEvent e){
         if(scene != null){         
            if(e.getKeyCode() == KeyEvent.VK_W){
               scene.stopForward();
            }         
            if(e.getKeyCode() == KeyEvent.VK_A){
               scene.stopLeft();
            }         
            if(e.getKeyCode() == KeyEvent.VK_S){
               scene.stopBack();
            }         
            if(e.getKeyCode() == KeyEvent.VK_D){
               scene.stopRight();
            }
         }
      }
      public void keyTyped(KeyEvent e){}
   }
   private class MouseClick implements MouseListener{
      public void mouseClicked(MouseEvent e){
         if(scene != null && playerShooting <= 0){
            scene.shoot(playerBuffer);
            playerShooting = 20;
         }
      }
      public void mouseExited(MouseEvent e){}
      public void mouseEntered(MouseEvent e){}
      public void mouseReleased(MouseEvent e){}
      public void mousePressed(MouseEvent e){}
   }
   private class Mouse implements MouseMotionListener{
      public void mouseMoved(MouseEvent e){
         int newX = e.getX();
         int change = (widthReal/2) - newX;
         if(scene != null){
            scene.turnAbs(change / 500.0);
         }
         robot.mouseMove(widthReal / 2, heightReal / 2);
      }
      public void mouseDragged(MouseEvent e){
         mouseMoved(e);
      }
   }
   public void paintComponent(Graphics g)
   {
      g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), null);
      g.drawImage(enemyImage, 0, 0, getWidth(), getHeight(), null);
      g.drawImage(playerImage, 0, 0, getWidth(), getHeight(), null);
   }
}