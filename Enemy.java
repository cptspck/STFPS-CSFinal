public class Enemy extends Entity {
   private Path path;
   private boolean triggered;
   private Entity[] enemies;
   private Entity other;
   private double FOV;
   public Enemy(double x, double y, double health, double dir, double fov, Weapon w, Path p){
      super(x, y, health, dir, w);
      path = p;
      triggered = false;
      FOV = fov;
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
   public void step(){
      for(int i = 0; i < enemies.length; i++){
         if(canSee(enemies[i])){
            
         }
      }
      if(triggered){
         setDir(Math.atan((other.getX() - getX())/(other.getY() - getY())));
      } else {
         path.move(this);
      }
   }
}