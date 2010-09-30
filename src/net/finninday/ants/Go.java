package net.finninday.ants;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.Timer;

import javax.swing.JFrame;

public class Go {

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("Ant simulation");
		Container c = frame.getContentPane();
		c.setLayout(new BorderLayout());
		Field field = new Field();
		c.add(field, BorderLayout.CENTER);
		frame.setSize(304, 324);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		while (true) {
			Thread.currentThread().sleep(20);
			field.repaint();
		}
	}

}
