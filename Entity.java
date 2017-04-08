/*
Keegan Lanzillotta
Version 0.1
April 7, 2017
*/

public abstract class Entity implements Runnable {
   private double myX;
   private double myY;
   private double myHealth;
   private double myDir;
   public Entity(double x, double y, double health, double dir){
      myX = x;
      myY = y;
      myHealth = health;
      myDir = dir;
   }
   public abstract void step();
   public void run(){
      step();
   }
}