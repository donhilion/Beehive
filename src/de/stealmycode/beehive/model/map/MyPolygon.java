package de.stealmycode.beehive.model.map;

import java.awt.Polygon;
import java.awt.Rectangle;

import org.newdawn.slick.util.Log;

public class MyPolygon extends Polygon{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4848120303084810L;
	
	private Rectangle him = new Rectangle();
	
	public MyPolygon(int[] xArray, int[] yArray, int numberOfPoits)
	{
		super(xArray, yArray, numberOfPoits);
	}
	
	public boolean collidesWith(int x, int y) {
		him.setBounds(x, y, 1, 1);
		
		return this.intersects(him);	
	}

}
