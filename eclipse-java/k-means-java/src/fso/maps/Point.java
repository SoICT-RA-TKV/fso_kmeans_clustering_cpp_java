package fso.maps;

import java.text.*;

public class Point {
	
	private double x;
	private double y;
	
	static public double EPS = 1e-9;

	public Point(Point other) {
		this(other.getX(), other.getY());
	}

	public Point(double _x, double _y) {
		setX(_x);
		setY(_y);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setX(double _x) {
		x = _x;
	}

	public void setY(double _y) {
		y = _y;
	}

	public double distance(Point other) {
		return Math.sqrt(Math.pow((x - other.x), 2) + Math.pow((y - other.y), 2));
	}

	public String toDecimalString() {
		DecimalFormat df = new DecimalFormat("0.000");
		return df.format(x) + " " + df.format(y) + " 0.0";
	}

	public String toCircle(double radius) {
		DecimalFormat df = new DecimalFormat("0.000");
		return "(x-" + df.format(x) + ")^2 +(y-" + df.format(y) + ")^2 = " + df.format(radius * radius);
	}

	public boolean equals(Point other) {
		if ((Math.abs(this.x - other.x) < EPS) && (Math.abs(this.y - other.y) < EPS)) {
			return true;
		}
		return false;
	}

	public String toLine(Point other) {
		DecimalFormat df = new DecimalFormat("0.000");
		return "(" + df.format(this.x) + "+"
				+ df.format(other.x - this.x) + "t,"
				+ df.format(this.y) + "+"
				+ df.format(other.y - this.y) + "t)";
	}
}