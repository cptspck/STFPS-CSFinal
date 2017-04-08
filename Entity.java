/*
Keegan Lanzillotta
Version 0.1
April 7, 2017
*/

public abstract class Entity implements Runnable {
   protected double myX;
   protected double myY;
   protected double myHealth;
   protected double myDir;
   protected Weapon myWeapon;
   public Entity(double x, double y, double health, double dir, Weapon w){
      myX = x;
      myY = y;
      myHealth = health;
      myDir = dir;
      myWeapon = w;
   }
   protected abstract void step();
   public void run(){
      step();
   }
}