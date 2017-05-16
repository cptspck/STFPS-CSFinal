import java.applet.*;
public class Sound{
   public static AudioClip getClip(String arg){
      try{
         java.net.URL file = Sound.class.getResource(arg);
         return Applet.newAudioClip(file);
      }catch(Exception e){
         System.out.println("Error-GetClip");
         return null;
      }
   }
   public static void loop(AudioClip arg){
      try{
         arg.loop();
      }catch(Exception e){
         System.out.println("Error-Loop");
      }
   }
   public static void play(AudioClip arg){
      try{
         arg.play();
      }catch(Exception e){
         System.out.println("Error-Play");
      }
   }
   public static void stop(AudioClip arg){
      try{
         arg.stop();
     }catch(Exception e){
         System.out.println("Error-Stop");
      }
  }
}
