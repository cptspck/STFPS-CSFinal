import javax.swing.*;
import java.awt.*;
import java.awt.AlphaComposite;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.SwingUtilities;

public class LoadPanel extends JPanel{
   private BufferedImage image;
   private Graphics g;
   private boolean hasFile;
   private JTextField box;
   private String input;
   public LoadPanel(){
      int w = 800;
      int h = 500;
      image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
      g = image.getGraphics();
      hasFile = false;
      
      addKeyListener(new Key());
      setFocusable(true);
      
      g.setColor(Color.BLACK);
      g.fillRect(0, 0, 800, 500);
      
      g.setColor(new Color(255, 153, 0));
      g.setFont(new Font("Arial", Font.BOLD, 60));
      g.drawString("STFPS: Dorvan V", 120, 100);
      
      input = "";
      
      
   }
   public String getFileName(){
      while(!hasFile){}
      return input;
   }
      public void keyReleased(KeyEvent e){         
         if(e.getKeyCode() == KeyEvent.VK_Q && e.getModifiers() == KeyEvent.CTRL_MASK){
            System.exit(0);
         }
      }
      public void keyTyped(KeyEvent e){}
   }
   private void close(){
      System.out.println("exiting");
      Window frame = SwingUtilities.getWindowAncestor(this);
      frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
   }
   public void paintComponent(Graphics g)
   {
      g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
   }
}