public class Player extends Entity {
   private double dX, dY, speed;
   public Player(double x, double y, double health, double dir, Weapon w, double s){
      super(x, y, health, dir, w);
      speed = s;
     
   }
   protected void step(){
      super.myX += dX;
      super.myY += dY;
   }
   public void forward(){
      dX = Math.sin(myDir) * speed;
      dY = Math.cos(myDir) * speed;
   }
   public void back(){
      dX = Math.sin(myDir - Math.PI) * speed;
      dY = Math.cos(myDir - Math.PI) * speed;
   }
   public void left(){
      dX = Math.sin(myDir + (Math.PI / 2.0)) * speed;
      dY = Math.cos(myDir + (Math.PI / 2.0)) * speed;
   }
   public void right(){
      dX = Math.sin(myDir - (Math.PI / 2.0)) * speed;
      dY = Math.cos(myDir - (Math.PI / 2.0)) * speed;
   }
}