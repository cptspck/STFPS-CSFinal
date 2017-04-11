import java.util.concurrent.TimeUnit;

public abstract class Entity implements Runnable {
   protected double myX;
   protected double myY;
   protected double myHealth;
   protected double myDir;
   protected Weapon myWeapon;
   protected int myFaction;
   public Entity(double x, double y, double health, double dir, Weapon w, int faction){
      myX = x;
      myY = y;
      myHealth = health;
      myDir = dir;
      myWeapon = w;
      myFaction = faction;
   }
   protected abstract void step();
   public void run(){
      step();
      try{
         TimeUnit.MILLISECONDS.sleep(500);
      } catch(Exception e){}
   }
   public double getX(){
      return myX;
   }
   public double getY(){
      return myY;
   }
   public double getHealth(){
      return myHealth;
   }
   public double getDir(){
      return myDir;
   }
   public Weapon getWeapon(){
      return myWeapon;
   }
   public int getFaction(){
      return myFaction;
   }
   public void setX(double x){
      myX = x;
   }
   public void setY(double y){
      myY = y;
   }
   public void setHealth(double h){
      myHealth = h;
   }
   public void setDir(double d){
      myDir = d;
   }
}