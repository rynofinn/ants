package net.finninday.ants;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class Food {

	public FloatPoint location;
	public String name;
	public int index;
	public int amount;
	static int number;
	Trail trail;
	Graphics2D g2;
	
	public Food(Field f) {
		amount = (int)(Math.random()*7.0+3.0);
		location = new FloatPoint(
				(int)(Math.random()*f.getWidth()),
				(int)(Math.random()*f.getHeight()));
		name = "Food #"+ ++number;
		index = number;
	}
	
	public void takeBite() {
		amount = amount - 1;
		//System.out.println("Bite taken from "+name+"! amount: "+amount);
		if (amount <= 0){
			//location = new Point(-1,-1);
		}
	}

	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g2 = (Graphics2D)g;
		Shape n = new Ellipse2D.Float((int)location.getX()-amount/2, 
									  (int)location.getY()-amount/2, amount, amount);
		g2.setPaint(Color.red);
		g2.fill(n);
		g2.draw(n);
	}
}
