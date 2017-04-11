import java.util.*;
/*
FILE FORMAT:
<speed>
<number of spots>
<x1> <y1>
<x2> <y2>
<x3> <y3>
<x4> <y4>
<x5> <y5>
*/

public class Path{
   private double speed;     //how far the NPC moves in each step()
   private double[] xList;  //a list of x coordinates on the path
   private double[] yList; //same, but with the corresponding Ys instead
   private int cur;
   private String filename;
   public Path(Scanner in, String name){
      filename = name;
      cur = 0;
      speed = in.nextDouble();
      int size = in.nextInt();
      
      xList = new double[size];
      yList = new double[size];
      
      for(int i = 0; i < size; i ++){
         xList[i] = in.nextDouble();
         yList[i] = in.nextDouble();
      }
   }
   public double getSpeed(){
      return speed;
   }
   public void nextPos(Enemy e){
      e.setDir(Math.atan((xList[cur] - e.getX())/(yList[cur] - e.getY())));
   }
   public void move(Enemy e){
      double prevDistX = xList[cur] - e.getX();
      double prevDistY = yList[cur] - e.getY();
      
      double newX = e.getX() + (Math.sin(e.getDir()) * speed);
      double newY = e.getY() + (Math.cos(e.getDir()) * speed);
      
      double newDistX = xList[cur] - newX;
      double newDistY = yList[cur] - newY;
      
      if((newDistX * prevDistX) <= 0){
         newX = xList[cur];
         newY = yList[cur];
         
         cur++;
         if(cur <= xList.length - 1){
            cur = 0;
         }
         move(e);
      }
      e.setX(newX);
      e.setY(newY);
   }
   public String getSave(){
      return filename;
   }
}