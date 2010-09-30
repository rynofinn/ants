package net.finninday.ants;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.BeforeClass;
import org.junit.Test;

public class AntTest {

	static Field f;
	static Nest n;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		f = new Field();
		n = new Nest(f);
		
	}

	@Test
	public void testAnt() {
		Ant a = new Ant(f, n.location);
		assert(a.location.equals(n.location));
	}

	@Test
	public void testRun() {
		//fail("Not yet implemented");
	}

	@Test
	public void testMoveFrom() {
		Ant a = new Ant(f, n.location);
		FloatPoint p = a.moveFrom(a.location);
		assert(!p.equals(a.location));
		
	}
	
	@Test
	public void testMoveTo() {
		FloatPoint p;
		Ant a;
		a = new Ant(f, new FloatPoint(50,50));
		
		// should increase x, y shouldn't change
		p = a.moveTo(new FloatPoint(100,50));
		assert (p.x > 50);
		assert (p.y == 50);
		
		// should decrease x, y shouldn't change
		p = a.moveTo(new FloatPoint(20,50));
		assert (p.x < 50);
		assert (p.y == 50);
		
		// should decrease y, x shouldn't change
		p = a.moveTo(new FloatPoint(50,0));
		assert (p.x == 50);
		assert (p.y < 50);
		
		// should increase y, x shouldn't change
		p = a.moveTo(new FloatPoint(50,100));
		assert (p.x == 50);
		assert (p.y > 50);
	}
	@Test
	public void testMoveToTrig() {
		FloatPoint p;
		Ant a;
		a = new Ant(f, new FloatPoint(50,50));
		
		// should increase x, y shouldn't change
		p = a.moveToTrig(new FloatPoint(100,50));
		assert (p.x > 50 && p.x < 55);
		assert (p.y == 50);
		
		// should decrease x, y shouldn't change
		p = a.moveToTrig(new FloatPoint(20,50));
		assert (p.x < 50 && p.x > 45);
		assert (p.y == 50);
		
		// should decrease y, x shouldn't change
		p = a.moveToTrig(new FloatPoint(50,0));
		assert (p.x == 50);
		assert (p.y < 50 && p.y > 45);
		
		// should increase y, x shouldn't change
		p = a.moveToTrig(new FloatPoint(50,100));
		assert (p.x == 50);
		assert (p.y > 50 && p.y < 55);
		
		// should decrease y and decrease x
		p = a.moveToTrig(new FloatPoint(25,25));
		assert (p.x < 50);
		assert (p.y < 50);
		
		// should increase x and increase y
		p = a.moveToTrig(new FloatPoint(75,75));
		assert(p.x > 50);
		assert(p.y > 50);
	}
}
