import java.util.*;
import java.io.*;
/*
SAVE FILE FORMAT:

<player's x>
<player's y>
<player's dir>
<player's health>
<player's map (<name>.map file)>
<player's weapon (<name>.wp file)>
<player's speed>
*/
public class Save {
   private String m;
   private Player player;
   public Save(Scanner in){
      double x = in.nextDouble();
      double y = in.nextDouble();
      double d = in.nextDouble();
      double h = in.nextDouble();
      String m = in.next();
      String w = in.next();
      double s = in.nextDouble();
      
      Weapon weapon;
      try{
         weapon = new Weapon(new Scanner(new File("weapons/" + w + ".wp")));
      } catch(FileNotFoundException e){
         System.out.println("Player has bad Weapon");
         System.exit(0);
         return;
      }
      player = new Player(x, y, h, d, weapon, s);
   }
   public Player getPlayer(){
      return player;
   }
}