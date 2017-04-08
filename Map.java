/*
Keegan Lanzillotta
Version 0.1
April 7, 2017
*/
import java.util.*;
import java.io.*;

public class Map {
   public Map(String filename){
      try {
         Scanner in = new Scanner(new File("maps/" + filename));
      } catch (FileNotFoundException e){
         System.out.println("Error: File \"" + filename + "not found");
      }
   }
}