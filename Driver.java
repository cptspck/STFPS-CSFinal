/*
Keegan Lanzillotta
Version 0.1
April 7, 2017
*/
import javax.swing.JFrame;


public class Driver{
   public static void main(String [] args){
      JFrame frame = new JFrame();
      frame.setSize(2100, 1500);
      frame.setLocation(0, 0);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setContentPane(new Panel(700, 500));
      frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
      frame.setUndecorated(true);
      frame.setVisible(true);
   }
}