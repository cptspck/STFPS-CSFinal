import java.util.*;
import java.io.*;
/*
NPC FILE FORMAT:
                  <number of NPCs>
(for each NPC:)   <NPC x> <NPC y> <NPC health> <NPC dir> <NPC FOV> <NPC faction> <NPC weapon (<name>.wp file)> <NPC path (<name>.path file)>
*/

public class NPC{
   private Enemy[] npc;
   public NPC(Scanner in){
      npc = new Enemy[in.nextInt()];
      for(int i = 0; i < npc.length; i ++){
         double x = in.nextDouble();
         double y = in.nextDouble();
         double h = in.nextDouble();
         double d = in.nextDouble();
         double f = in.nextDouble();
         int faction = in.nextInt();
         String wf = in.next();
         String pf = in.next();
         Weapon w;
         Path p;
         try{
            w = new Weapon(new Scanner(new File("weapons/" + wf + ".wp")), wf);
         } catch (FileNotFoundException e){
            System.out.println("FILE NOT FOUND: weapons/" + wf + ".wp");
            return;
         }
         try{
            p = new Path(new Scanner(new File("path/" + pf + ".path")), pf);
         } catch (FileNotFoundException e){
            System.out.println("FILE NOT FOUND: weapons/" + pf + ".wp");
            return;
         }
         npc[i] = new Enemy(x, y, h, d, f, w, p, faction, npc);
      }
   }
   public void save(Writer out) throws Exception{
      String output = "";
      output += npc.length + " ";
      for(int i = 0; i < npc.length; i ++){
         output += npc[i].getX() + " ";
         output += npc[i].getY() + " ";
         output += npc[i].getHealth() + " ";
         output += npc[i].getDir() + " ";
         output += npc[i].getFOV() + " ";
         output += npc[i].getWeapon().getSave() + " ";
         output += npc[i].getPath().getSave() + " ";
      }
   }
}