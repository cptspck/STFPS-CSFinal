import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.AlphaComposite;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class Loader{
   private static Map map;
   private static Key key;
   private static Mouse mouse;
   private static Save save;
   private static NPC npc;
   private static Scene scene;
   private static String input;
   private static double widthReal, heightReal;
   private static Graphics g;
   private static Panel p;
   public static void load_init(Graphics graphics, Panel panel) {
      //make these variables global for the listener to use them      
      input = "START";
      
      key = new Key();
      mouse = new Mouse();
      g = graphics;
      p = panel;
      
      g.setColor(Color.BLACK);
      g.fillRect(0, 0, 800, 450);
      
      g.setColor(new Color(255, 153, 0));
      g.setFont(new Font("Arial", Font.BOLD, 60));
      g.drawString("STFPS: Dorvan V", 160, 120);
      
      g.setColor(new Color(153, 153, 204));
      g.fillRect(300, 250, 200, 70);
      g.fillRect(200, 350, 400, 70);
      
      g.setColor(new Color(0, 17, 238));
      g.setFont(new Font("Arial", Font.BOLD, 40));
      g.drawString("PLAY", 350, 300);
      g.setFont(new Font("Monospace", Font.BOLD, 35));
      g.drawString(input + " |", 200, 400);
      
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      widthReal = screenSize.getWidth();
      heightReal = screenSize.getHeight();
      
      p.addKeyListener(key);
      p.addMouseListener(mouse);
   }
   
   private static class Key implements KeyListener {
      public void keyPressed(KeyEvent e){
      //get ASCII value of keypress
         int val = e.getKeyCode();
      //convert that into a single-character long string for concantenation later
         String str = KeyEvent.getKeyText((Integer)e.getKeyCode());
         //System.out.println(val);
         if(val >= 65 && val <= 90){
         //if it is an alpha character, append it to the input string
            input += str;
         } else if(val == 8){
         //if its the backspace key, remove last character from input string
            input = input.substring(0, input.length() - 1);
         }
            g.setFont(new Font("Monospace", Font.BOLD, 35));
            //check to make sure string isn't too long, remove characters until it isn't
            while(g.getFontMetrics().stringWidth(input + " |") > 400){
               input = input.substring(0, input.length() - 1);
            }
            //draw new string on screen for user to see
            g.setColor(new Color(153, 153, 204));
            g.fillRect(200, 350, 400, 70);
      
            g.setColor(new Color(0, 17, 238));
            String draw = input + " |";
            g.drawString(draw, 200, 400);
            //repaint the panel
            p.repaint();         
      }
      public void keyReleased(KeyEvent e){}
      public void keyTyped(KeyEvent e){}
   }
   private static class Mouse implements MouseListener{
      public void mouseReleased(MouseEvent e){
         //System.out.println("X: " + e.getX() + "\t\t|\tY: " + e.getY());
         if(((e.getX() / widthReal) <= 0.625) && ((e.getX() / widthReal) >= 0.375)){
            if(((e.getY() / heightReal) <= 0.711) && ((e.getY() / heightReal) >= 0.555)){
               chooseFile();
            }
         }
      }
      public void mousePressed(MouseEvent e){}
      public void mouseClicked(MouseEvent e){}
      public void mouseExited(MouseEvent e){}
      public void mouseEntered(MouseEvent e){}
   }
   private static void chooseFile(){
   //paint over previous error message
      g.setColor(Color.BLACK);
      g.fillRect(150, 170, 500, 25);
      p.repaint();
      //take input and open the files
      String filename = input.toLowerCase();
      if(filename.equals("start")){
         p.playIntro();
         //delete the listeners, not needed anymore
         p.removeKeyListener(key);
         p.removeMouseListener(mouse);
      }
      
      try {
         save = new Save(new Scanner(new File("save/" + filename + ".save")));
         npc = new NPC(new Scanner(new File("npc/" + filename + ".npc")));
      } catch(FileNotFoundException e){
      //tell the user that the file wasn't found
         g.setColor(Color.RED);
         g.setFont(new Font("Arial", Font.BOLD, 18));
         g.drawString("FILE NOT FOUND: " + filename, 150, 190);
         
         p.repaint();
         return;
      }
      //give the user some feedback that things are working
      g.setColor(Color.GREEN);
      g.setFont(new Font("Arial", Font.BOLD, 18));
      g.drawString("Success! Loading...", 150, 190);
      p.repaint();
      //make the scene object
      scene = new Scene(save, npc);
      //delete the listeners, not needed anymore
      p.removeKeyListener(key);
      p.removeMouseListener(mouse);
      
      //give the panel the scene
      p.loaded(scene);
   }
   public static String getFile(){
      return input;
   }
}