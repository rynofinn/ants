package net.finninday.ants;

import java.awt.Point;

public class FloatPoint {
	public double x;
	public double y;
	public FloatPoint(double x2, double y2) {
		x = x2;
		y = y2;
	}
	public int getIntX() {
		return (int)x;
	}
	public int getIntY() {
		return (int)y;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public int distance(FloatPoint location) {
		Point p = new Point((int)location.x, (int)location.y);
		return (int) p.distance(x,y);
	}
	public double distance(Point point) {
		return point.distance(x,y);
	}
}
