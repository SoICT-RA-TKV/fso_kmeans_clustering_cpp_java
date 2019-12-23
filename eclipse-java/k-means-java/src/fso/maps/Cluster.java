package fso.maps;

import java.util.*;

public class Cluster {
	private Point centroid;
	public List<Point> pointOfCluster;
	public List<Integer> idPointOfCluster;

	public Cluster(Point center) {
		centroid = new Point(center);
		pointOfCluster = new ArrayList<Point>();
		idPointOfCluster = new ArrayList<Integer>();
	}

	public void add(Point point) {
		pointOfCluster.add(point);
	}

	public void addId(int u) {
		idPointOfCluster.add(u);
	}

	public void remove(Point point) {
		pointOfCluster.remove(point);
	}

	public void removeId(int u) {
		idPointOfCluster.remove((Integer) u);
	}

	public Point center() {
		if (pointOfCluster.size() == 0)
			return centroid;

		Point average = new Point(0, 0);
		for (Point point : pointOfCluster) {
			average.setX(average.getX() + point.getX());
			average.setY(average.getY() + point.getY());
		}
		int pointCount = pointOfCluster.size();
		average.setX(average.getX() / pointCount);
		average.setY(average.getY() / pointCount);
		return average;
	}

	public Point getCentroid() {
		return centroid;
	}

	public int getSize() {
		return pointOfCluster.size();
	}

	public double maxDistance() {
		double res = 0;
		for (Point p : pointOfCluster)
			res = Math.max(res, p.distance(centroid));
		return res;
	}

	public boolean converged() {
		if (centroid.equals(center()))
			return true;
		return false;
	}

	public void color(int[] col, int pattern) {
		for (Integer u : idPointOfCluster)
			col[u] = pattern;
	}
}