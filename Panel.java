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
   
   protected boolean waiting = false;
    
   private int playerShooting = 0;
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
      new Thread(new Runnable() {
         public void run(){
            Intro.playIntro(bgBuffer, panel, "text.png");
            try{
               Thread.sleep(5000);
            } catch(Exception e){}
            Intro.playIntro(bgBuffer, panel, "rock.png");
            waiting = false;
         }
      }).start();
      new Thread(new Runnable() {
         public void run(){
            playerBuffer.drawImage(new ImageIcon("images/defiant.png").getImage(), 300, 275, 200, 150, null);
            
            playerBuffer.setColor(Color.WHITE);
            playerBuffer.setFont(new Font("Arial", Font.ITALIC, 18));
            playerBuffer.drawString("Welcome, Commander, to the Defiant.  We will arrive at Dorvan V shortly.", 5, 40);
            panel.repaint();
            try{
               Thread.sleep(5000);
            } catch(Exception e){}
            for(int x = 0; x < 800; x ++){
               for(int y = 0; y <= 50; y ++){
                  playerImage.setRGB(x, y, 0);
               }
            }
            playerBuffer.drawString("Once we get there, your mission will be to infiltrate the Marquis", 5, 40);
            playerBuffer.drawString("compound under cover of a Cardassian attack.", 5, 60);
            
            panel.repaint();
            try{
               Thread.sleep(6000);
            } catch(Exception e){}
            for(int x = 0; x < 800; x ++){
               for(int y = 0; y <= 80; y ++){
                  playerImage.setRGB(x, y, 0);
               }
            }
            playerBuffer.drawString("Your target is Jecto Dulbisi. Mr. Jecto is the leader of the Marquis.", 5, 40);
            playerBuffer.drawString("Take him out, and the Andorian ambassador Ithisil Ch'ravak,", 5, 60);
            playerBuffer.drawString("who has infiltrated the Marquis, can take over.", 5, 80);
            panel.repaint();
            try{
               Thread.sleep(10000);
            } catch(Exception e){}
            for(int x = 0; x < 800; x ++){
               for(int y = 0; y <= 140; y ++){
                  playerImage.setRGB(x, y, 0);
               }
            }
            panel.repaint();
            try{
               Thread.sleep(3000);
            } catch(Exception e){}
            playerBuffer.drawString("We have arrived, Commander. We can't beam you down, as that would leave", 5, 40);
            playerBuffer.drawString("a Starfleet transporter trace. Instead, you will take a shuttlecraft.", 5, 60);
            try{
               Thread.sleep(8000);
            } catch(Exception e){}
            for(int x = 0; x < 800; x ++){
               for(int y = 0; y < 450; y ++){
                  playerImage.setRGB(x, y, 0);
               }
            }
            playerBuffer.drawImage(new ImageIcon("images/shuttle.png").getImage(), 300, 325, 200, 100, null);
         }
      }).start();
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
         if(scene != null)
            scene.stopMovement();
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
         int newX = (e.getX() * width) / widthReal;
         
         if(scene != null){
            if(newX > 450){
               scene.turnRight((newX - 450) / 50);
            }else if(newX < 350){
               scene.turnLeft((350 - newX) / 50);
            } else {
               scene.stopTurn();
            }
         }
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