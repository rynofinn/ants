package net.finninday.ants;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JComponent;
import javax.swing.SwingWorker;
import java.util.Timer;

public class Ant extends JComponent implements Runnable {

	public enum AntState {
		foraging, hauling, following, eating
	};

	FloatPoint location;
	double realX;
	double realY;
	private AntState state;
	public Graphics2D g2;
	public int vector;
	public boolean alive;
	public static int number;
	private Thread t;
	private int delay = 20;
	private Field parent;
	String name;
	public Food myFood;
	public FloatPoint myTarget;
	public Trail myTrail;

	/**
	 * vector is a direction of motion represented by an integer from 0 to 7 -1
	 * indicates no motion 0 indicates motion due north greater numbers proceed
	 * clockwise, 1 = NE, 2 = E, 3 = SE
	 * @param field 
	 * 
	 */

	public Ant(Field field, FloatPoint p) {
		parent = field;
		state = AntState.foraging;
		location = p;
		vector = -1; // not moving
		alive = true;
		t = new Thread(this);
		t.setName("ant"+ ++number);
		t.start();
		name = "Ant #"+number;
		//System.out.println("new ant created: "+name);
	}
	
	public void run() {
		//System.out.println("in ant thread...");
		
		while (alive) {
			switch (state) {
			case foraging:
				// move randomly, looking for food
				location = moveFrom(location);
				if (senseFood()) {
					state = AntState.eating;
				}
				Food foodHint = parent.senseTrail(location);
				if (foodHint != null) {
					state = AntState.following;
					myTarget = foodHint.location;
					myTrail = foodHint.trail;
				}
				break;
			case hauling:
				// move toward the nest and leave a chemical trail
				//System.out.println(name+" is hauling at location "+location.x+" "+location.y);
				if (location.distance(parent.nest.location)<5) {
					state = AntState.foraging;
				} else {
					myFood.trail.addStep(location);
					location = moveToTrig(parent.nest.location);
				}
				break;
			case following:
				if (location.distance(myTarget) < 5) {
					state = AntState.foraging;
					myTarget = null;
				} else {
					location = moveToTrig(myTarget);
					myTrail.decay(location);
				}
				break;
			case eating:
				// take a bite and then haul
				myFood = parent.foodNear(location);
				if (myFood != null) {
					myFood.takeBite();
					state = AntState.hauling;
				} else {
					System.out.println(name +": where did the food go?");
					state = AntState.foraging;
				}
				break;
			}

			try {
				Thread.currentThread().sleep(delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	protected FloatPoint moveTo(FloatPoint floatPoint) {
		// move one step toward goal of point p
		// we could calculate the trig functions to determine our course,
		// but I'm going to try a shortcut of just determining slope
		double ax = location.getX();
		double ay = location.getY();
		double bx = floatPoint.getX();
		double by = floatPoint.getY();
		double dx = bx-ax;
		double dy = by-ay;
		double sx;
		double sy;
		double scaleFactor;
		double nx;
		double ny;
		
		scaleFactor = Math.max(Math.abs(dx), Math.abs(dy));
		
		sx = dx/scaleFactor;
		sy = dy/scaleFactor;

		nx = ((ax+sx));
		ny = ((ay+sy));
		
		return new FloatPoint(nx,ny);
	}
	
	protected FloatPoint moveToTrig(FloatPoint location2) {
		double ax = location.getX();
		double ay = location.getY();
		double bx = location2.getX();
		double by = location2.getY();
		double dx = bx-ax;
		double dy = by-ay;
		double course = 0;
		double tangent; 
		double sx;
		double sy;
		if (dx >=0){
			if (dy >=0){
				//System.out.println("target is in 2nd quadrant");
				tangent = Math.atan(Math.abs(dy/dx));
				course = Math.PI/2 + tangent;
				sy = Math.sin(course-Math.PI/2);
				sx = Math.sin(Math.PI-course);
			} else {
				//System.out.println("target is in 1st quadrant");
				tangent = Math.atan(Math.abs(dy/dx));
				course = Math.PI/2 - tangent;
				sx = Math.sin(course);
				sy = -Math.sin(Math.PI/2-course);
			}
		} else {
			if (dy>=0) {
				//System.out.println("target is in 3rd quadrant");
				tangent = Math.atan(Math.abs(dy/dx));
				course = (3*Math.PI)/2 - tangent;
				sx = Math.sin(Math.PI-course);
				sy = Math.sin((3*Math.PI)/2-course);
			} else {
				//System.out.println("target is in 4th quadrant");
				tangent = Math.atan(Math.abs(dy/dx));
				course = (3*Math.PI)/2 + tangent;
				sy = -Math.sin(course - (3*Math.PI)/2);
				sx = -Math.sin(2*Math.PI-course);
			}
		}
		//sx = sx*2;
		//sy = sy*2;
		//System.out.println("Course to target = "+(int)Math.toDegrees(course));
		//System.out.println("step x = "+sx+", step y = "+sy);
		return new FloatPoint((ax + sx), (ay + sy));
	}

	private boolean senseFood() {
		if (parent.isFoodNear(location)) {
			return true;
		} else {
			return false;
		}
	}

	public void paint(Graphics g) {
		Color c;
		switch (state) {
		case eating:
			c = Color.yellow;
			break;
		case hauling:
			c = Color.pink;
			break;
		case following:
			c = Color.white;
			break;
		default:
			c = Color.black;
		}
		g2 = (Graphics2D) g;
		Shape n = new Ellipse2D.Float((int) location.getX()-1,
				(int) location.getY()-1, 2, 2);
		g2.setPaint(c);
		g2.fill(n);
		g2.draw(n);
		//System.out.println("updating ant graphic "+ this.number);
	}
	
	protected FloatPoint moveFrom(FloatPoint location2) {
		if (vector == -1) {
			vector = (int) (Math.random() * 8.0) + 1; // determine initial
														// vector
		} else {
			int turn = (int) (Math.random() * 3.0) - 1; // determine change of
														// course (-1 to 1)
			vector = vector + turn;
			if (vector > 7) {
				vector = vector % 8;
			}
			if (vector < 0) {
				vector = vector + 8;
			}
		}
		// apply vector to get new location
		double x = location.getX();
		double y = location.getY();
		switch (vector) {
		case 0:
			y = y - 1;
			break;
		case 1:
			x = x + 1;
			y = y - 1;
			break;
		case 2:
			x = x + 1;
			break;
		case 3:
			x = x + 1;
			y = y + 1;
			break;
		case 4:
			y = y + 1;
			break;
		case 5:
			x = x - 1;
			y = y + 1;
			break;
		case 6:
			x = x - 1;
			break;
		case 7:
			x = x - 1;
			y = y - 1;
			break;
		}
		if (x>=parent.getWidth()) x=parent.getWidth()-1;
		if (x<0) x=0;
		if (y>=parent.getHeight()) y=parent.getHeight()-1;
		if (y<0) y=0;
		location = new FloatPoint(x, y);
		return location;
	}


}
