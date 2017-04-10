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
   private static Save save;
   private static NPC npc;
   private static boolean available;
   private static Scene scene;
   private static String input;
   private static double widthReal, heightReal;
   public static void load_init(Graphics g, Panel p) {
      available = false;
      g.setColor(Color.BLACK);
      g.fillRect(0, 0, 800, 450);
      
      g.setColor(new Color(255, 153, 0));
      g.setFont(new Font("Arial", Font.BOLD, 60));
      g.drawString("STFPS: Dorvan V", 160, 120);
      
      g.setColor(new Color(153, 153, 204));
      g.fillRect(300, 250, 200, 70);
      
      g.setColor(Color.BLACK);
      g.setFont(new Font("Arial", Font.BOLD, 40));
      g.drawString("PLAY", 350, 300);
      
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      widthReal = screenSize.getWidth();
      heightReal = screenSize.getHeight();
      
      input = "";
      
      p.addKeyListener(new Key());
      p.addMouseListener(new Listener());
   }
   
   private static class Key implements KeyListener {
      public void keyPressed(KeyEvent e){
         int val = e.getKeyCode();
         String str = KeyEvent.getKeyText((Integer)e.getKeyCode());
         System.out.println(val);
         if(val >= 65 && val <= 90){
            input += str;
            System.out.println(input);
         }         
      }
      public void keyReleased(KeyEvent e){}
      public void keyTyped(KeyEvent e){}
   }
   private static class Listener implements MouseListener{
      public void mouseReleased(MouseEvent e){
         System.out.println("X: " + e.getX() + "\t\t|\tY: " + e.getY());
         if(e.getX() / widthReal <= 0.625 && e.getX() >= 0.375){
            if(e.getY() / heightReal <= 0.711 && e.getY() >= 0.555){
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
      
      String filename = input.toLowerCase();
      
      try {
         map = new Map(new Scanner(new File("map/" + filename + ".map")));
         save = new Save(new Scanner(new File("save/" + filename + ".save")));
         npc = new NPC(new Scanner(new File("npc/" + filename + ".npc")));
      } catch(FileNotFoundException e){
         System.out.println("file not found: " + filename);
         System.exit(0);
      }
      scene = new Scene(map, save, npc);
   }
}