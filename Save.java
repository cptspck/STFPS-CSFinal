/*
Keegan Lanzillotta
Version 0.1
April 7, 2017
*/
import java.util.*;
/*
SAVE FILE FORMAT:

<player's x>
<player's y>
<player's dir>
<player's health>
<player's map>
*/
public class Save {
   private String m;
   private Player player;
   public Save(Scanner in){
      int h = in.nextInt();
      String m = in.next();
      int x = in.nextInt();
      int y = in.nextInt();
      int d = in.nextInt();
      
      player = new Player(5.5, 5.4, 5.3, 5.7, new Weapon(5));
   }
}