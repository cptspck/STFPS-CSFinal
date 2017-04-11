//just defines numbers for factions and faction-related helper methods
public class Faction{
//absolute
   public final static int STARFLEET = 0;
   public final static int CARDASSIAN = 1;
   public final static int MAQUIS = 2;
   public final static int CIVILIAN = 4; //just in case we add civilians
 //relative  
   public final static int FRIEND = 10;
   public final static int ENEMY = 11;
   public final static int NEUTRAL = 12;
   
   public static boolean areEnemies(Entity a, Entity b){
      int af = a.getFaction();
      int bf = b.getFaction();
      
      if(af > bf){ //switch them so that af is <= bf
         int tmp = af;
         af = bf;
         bf = tmp;
      }
      
      if(af == bf){return false;}            //same faction are obviously not enemies
      if(af == STARFLEET && bf < CIVILIAN){return true;}    //starfleet and non-civilians are enemies
      if(af == STARFLEET && bf == CIVILIAN){return false;}   //starfleet and civilians arent enemies
      if(af < CIVILIAN && bf < CIVILIAN){return true;}     //maquis and cardassian are enemies
      if(af == MAQUIS && bf == CIVILIAN){return false;}  //maquis and civlians arent enemies
      if(af == CARDASSIAN && bf == CIVILIAN){return true;}   //cardassians hate civlians as well
      
      return false;
   }
   public static boolean areFriends(Entity a, Entity b){
      int af = a.getFaction();
      int bf = b.getFaction();
      
      if(af > bf){ //switch them so that af is <= bf
         int tmp = af;
         af = bf;
         bf = tmp;
      }
      if(af == bf){return true;} //same faction, must be friends
      if(af == MAQUIS && bf == CIVILIAN){return true;} //maquis and civilians are friends
      return false; //no other friends :(
      
   }
   public static boolean areNeutral(Entity a, Entity b){
      int af = a.getFaction();
      int bf = b.getFaction();
      
      if(af > bf){ //switch them so that af is <= bf
         int tmp = af;
         af = bf;
         bf = tmp;
      }
      
      return (af == STARFLEET) && (bf == CIVILIAN); //only two factions that are neutral to each other
   }
   public static int relationship(Entity a, Entity b){
      if(areEnemies(a, b))
         return ENEMY;
      if(areFriends(a, b))
         return FRIEND;
      else return NEUTRAL;
   }
}