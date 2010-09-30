package net.finninday.ants;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.util.Date;
import java.util.Vector;

public class Trail {
	public Vector<Point> path;
	Graphics2D g2;
	Food food;
	Field field;
	long created;
	long maxTime = 10000; // 10 second lifetime on trails
	
	public Trail(Field fld, Food f) {
		this.field = fld;
		path = new Vector<Point>();
		this.food = f;
		f.trail = this;
		Date now = new Date();
		created = now.getTime();
	}
	
	public void addStep(FloatPoint location) {
		Point p = new Point(location.getIntX(), location.getIntY());
		path.add(p);
	}

	public void paint(Graphics g) {
		g2 = (Graphics2D)g;
		g2.setColor(Color.yellow);
		for (int i=0; i<path.size(); i++){
			g2.draw(new Ellipse2D.Double(path.get(i).x, path.get(i).y, 1,1));
		}
	}

	public void decay(FloatPoint location) {
		Point p = new Point(location.getIntX(), location.getIntY());
		path.remove(p);
		Date check = new Date();
		Long now = check.getTime();
		if (now - created > maxTime) {
			path.removeAllElements();
			created = now;
		}
	}
}
