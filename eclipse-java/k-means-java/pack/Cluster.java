package pack;
import java.util.*;
import java.text.*;
import java.io.*;

public class Cluster{
  private ToaDo centroid;
  public List<ToaDo> pointOfCluster;
  public List<Integer> idPointOfCluster;

  public Cluster(ToaDo center){
    centroid = new ToaDo(center);
    pointOfCluster = new ArrayList<ToaDo>();
    idPointOfCluster = new ArrayList<Integer>();
  }
  public void add(ToaDo diem){
    pointOfCluster.add(diem);
  }
  public void addId(int u){
    idPointOfCluster.add(u);
  }
  public void remove(ToaDo diem) {
    pointOfCluster.remove(diem);
  }
  public void removeId(int u){
    idPointOfCluster.remove((Integer) u);
  }
  public ToaDo center(){
    if (pointOfCluster.size() == 0)
      return centroid;

    ToaDo average = new ToaDo(0,0);
    for (ToaDo diem: pointOfCluster){
      average.setX(average.getX() + diem.getX());
      average.setY(average.getY() + diem.getY());
    }
    int pointCount = pointOfCluster.size();
    average.setX(average.getX() / pointCount);
    average.setY(average.getY() / pointCount);
    return average;
  }
  public ToaDo getCentroid(){
    return centroid;
  }
  public int getSize() {
    return pointOfCluster.size();
  }
  public double maxDistance() {
    double res = 0;
    for (ToaDo p: pointOfCluster)
      res = Math.max(res, p.khoangCach(centroid));
    return res;
  }
  public boolean converged() {
    if (centroid.equals(center())) return true;
    return false;
  }
  public void color(int[] col,int pattern) {
    for (Integer u : idPointOfCluster)
      col[u] = pattern;
  }
}