import java.util.*;
public class Enemy extends Entity {
   private Path path;
   private boolean triggered;
   private boolean alive;
   private Entity target;
   private double FOV;
   private boolean canShoot;
   public Enemy(double x, double y, double health, double dir, double fov, Weapon w, Path p, int faction, Enemy[] others){
      super(x, y, health, dir, w, faction);
      path = p;
      triggered = false;
      alive = true;
      FOV = fov;
   }
   public boolean isAlive(){
      return alive;
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
      }
      if(myHealth <= 0){
         alive = false;
      }
   }
   public void trigger(Entity e){
      target = e;
      triggered = true;
   }
   public void step(){
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