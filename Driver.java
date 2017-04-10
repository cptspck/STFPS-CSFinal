import javax.swing.JFrame;

public class Driver{
   public static void main(String [] args){
      JFrame frame = new JFrame();
      frame.setSize(2100, 1500);
      frame.setLocation(0, 0);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setContentPane(new Panel(800, 450));
      frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
      frame.setUndecorated(true);
      frame.setVisible(true);
   }
}