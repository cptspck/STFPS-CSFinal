import java.util.*;
/*
FILE FORMAT:
<a> <b> <c> <d> <e> <f> <g>

all go into equation:
d(x) = ax^6 + bx^5 + cx^4 + dx^3 + ex^2 + fx + g
to get the damage d(x) at a distance x
*/
public class Weapon {
   private double powers[];
   private String filename;
   public Weapon(Scanner in, String name){
      //save filename for future reference
      filename = name;
      //save the data for the polynomial
      powers = new double[8];
      for(int i = 0; i < powers.length; i ++){
         powers[i] = in.nextDouble();
      }
   }
   public double getDamage(double dist){
      double out = 0;
      for(int i = 0; i < powers.length; i ++){
      //this should iterate through each term, adding each term to the total
         out += powers[i] * Math.pow(dist, powers.length - i - 1);
         //System.out.println(powers[i] + " * (" + dist + "^" + (powers.length - i -1) + ") = " + (powers[i] * Math.pow(dist, powers.length - i - 1)));
         //System.out.println("\t" + out);
      }
      if(out <= 0){
         out = 0;
      }
      return out;
   }
   public String getSave(){
      return filename;
   }
}