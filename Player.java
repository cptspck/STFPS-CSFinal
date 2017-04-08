/*
Keegan Lanzillotta
Version 0.1
April 7, 2017
*/

public class Player extends Entity {
   private double dX, dY;
   public Player(double x, double y, double health, double dir, Weapon w){
      super(x, y, health, dir, w);
     
   }
   protected void step(){
      super.myX += dX;
      super.myY += dY;
   }
   public void forward(){
      dX = Math.sin(myDir);
      dY = Math.cos(myDir);
   }
   public void back(){
      dX = Math.sin(myDir - Math.PI);
      dY = Math.cos(myDir - Math.PI);
   }
   public void left(){
      dX = Math.sin(myDir + (Math.PI / 2.0));
      dY = Math.cos(myDir + (Math.PI / 2.0));
   }
   public void right(){
      dX = Math.sin(myDir - (Math.PI / 2.0));
      dY = Math.cos(myDir - (Math.PI / 2.0));
   }
}