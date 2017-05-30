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
         
         System.out.println("X: " + xList[i] + "\t\t|\tY:" + yList[i]);
      }
      System.out.println();
   }
   public double getSpeed(){
      return speed;
   }
   public void nextPos(Enemy e){
      e.setDir(Math.atan((xList[cur] - e.getX())/(yList[cur] - e.getY())));
   }
   public void move(Enemy e){
      if(xList.length <= 0){
         return;
      }
      double prevDistX = xList[cur] - e.getX();
      double prevDistY = yList[cur] - e.getY();
      double newDir = Math.atan2(xList[cur] - e.getX(), yList[cur] - e.getY());
      
      e.setDir(newDir);
      
      double newX = e.getX() + (Math.sin(e.getDir()) * speed);
      double newY = e.getY() + (Math.cos(e.getDir()) * speed);
      
      double newDistX = xList[cur] - newX;
      double newDistY = yList[cur] - newY;
      
      if((newDistX * prevDistX) < 0){
         newX = xList[cur];
         newY = yList[cur];
         
         cur++;
         if(cur >= xList.length - 1){
            cur = 0;
         }
      }
      e.setX(newX);
      e.setY(newY);
      if(xList[cur] == e.getX() && e.getY() == yList[cur]){
         //System.out.println("\n\n\n\t\tNEXT!!!!\n");
         cur++;
      }
   }
   public String getSave(){
      return filename;
   }
}