import java.util.*;
public class Enemy extends Entity {
   private Path path;
   private boolean triggered;
   private HashMap<Entity, Integer> enemies;
   private Entity target;
   private double FOV;
   private boolean canShoot;
   public Enemy(double x, double y, double health, double dir, double fov, Weapon w, Path p, int faction, Enemy[] others){
      super(x, y, health, dir, w, faction);
      path = p;
      triggered = false;
      FOV = fov;
      enemies = new HashMap<Entity, Integer>();
      for(int i = 0; i < others.length; i ++){
         
      }
   }
   public boolean canSee(Entity e){
      double angle = Math.atan((e.getX() - getX())/(e.getY() - getY()));
      /*
                 >  [dir - FOV/2]
                /
               /
              / (e)
             /_/
       (this)/- - - - - -> [dir]
            \
             \
              \
               \
                \
                 >  [dir + FOV/2]
      */
      double leftBound = myDir + (2 * Math.PI) - (FOV/2);
      double rightBound = myDir + (FOV/2);
      return leftBound <= angle && angle <= rightBound;
   }
   public void shot(Weapon w, double distance){
      if(canShoot){
         double damage = w.getDamage(distance);
         myHealth -= damage;
         System.out.println("OW! \t\t|\tHealth: " + myHealth + "\t\t|Damage: " + damage + "\tAt range: " + distance);
      } else {
         System.out.println("miss");
      }
   }
   public void step(){
      for(Entity key: enemies.keySet()){
         if(Faction.areEnemies(key, this) && canSee(key) && !triggered){
            triggered = true;
            target = key;
         }
      }
      //if you have been triggered, look at the triggered individual
      if(triggered && canSee(target)){
         setDir(Math.atan((target.getX() - getX())/(target.getY() - getY())));
      } else {
         path.move(this);
      }
   }
   public void setShootability(boolean shootability){
      canShoot = shootability;
   }
   public double getFOV(){
      return FOV;
   }
   public Path getPath(){
      return path;
   }
}