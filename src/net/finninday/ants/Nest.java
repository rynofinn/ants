package net.finninday.ants;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import javax.swing.JComponent;

public class Nest extends JComponent {
	public FloatPoint location;
	Graphics2D g2;

	
	public Nest(Field f) {
		// put the nest in the middle of the field
		int x = (int)((Math.random()*(double)f.getWidth()*0.75)+f.getWidth()*0.25);
		int y = (int)((Math.random()*(double)f.getHeight()*0.75)+f.getHeight()*0.25);
		location = new FloatPoint(x,y);
		
	}
	
	public void paint(Graphics g) {
		g2 = (Graphics2D)g;
		Shape n = new Ellipse2D.Float((int)location.getX()-3, (int)location.getY()-3, 6, 6);
		g2.setPaint(Color.blue);
		g2.fill(n);
		g2.draw(n);
	}
}
