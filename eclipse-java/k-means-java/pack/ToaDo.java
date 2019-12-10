package pack;
import java.util.*;
import java.text.*;
import java.io.*;

public class ToaDo{
  private static DecimalFormat df;
  private double x,y;
  public ToaDo(ToaDo other){
    this(other.getX(),other.getY());
  }
  public ToaDo(double ax,double ay){
    setX(ax);
    setY(ay);
  }
  public double getX(){
    return x;
  }
  public double getY(){
    return y;
  }
  public void setX(double ax){
    x = ax;
  }
  public void setY(double ay){
    y = ay;
  }
  public double khoangCach(ToaDo other){
    return Math.sqrt( Math.pow((x - other.x),2) 
    + Math.pow((y - other.y),2) );
  }
  public String toDecimalString(){
    df = new DecimalFormat("0.000");
    return df.format(x) + " " + df.format(y) + " 0.0";
  }
  public String toCircle(double radius){
    df = new DecimalFormat("0.000");
    return "(x-" + df.format(x) +")^2 +(y-" + df.format(y) + ")^2 = " + df.format(radius * radius) ;
  }
  public boolean equals(ToaDo other) {
    double EPS = 1e-9;
    if ((Math.abs(this.x - other.x) < EPS) && (Math.abs(this.y - other.y) < EPS)) return true;
    return false;
  }
  public String toLine(ToaDo other) {
    df = new DecimalFormat("0.000");
    return "(" + df.format(this.x) + "+" + df.format(other.x - this.x) + "t," + df.format(this.y) + "+" + df.format(other.y - this.y) + "t)";
  }
}