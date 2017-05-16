import java.applet.*;

public class AudioTest{
   public static void main(String[] args){
//opens the file
      AudioClip aud = Sound.getClip("StarTrekOpening.wav");       
 Sound.play(aud); //starts the clip

      try{
    //pauses for 15 seconds (not accurate)
         Thread.sleep(15000); 
      }catch(Exception e){
         e.printStackTrace();
      }
      Sound.stop(aud); //stops the clip
   }
}
