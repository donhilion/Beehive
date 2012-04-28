package de.stealmycode.beehive.interfaces;

import de.stealmycode.beehive.utils.Direction;
import de.stealmycode.beehive.utils.Position;

public interface IDrawable {
	/**
	 *  
	 * @return The image-ID of the object that has to be drawn
	 */
	public int getImageID();
	
	/**
	 * 
	 * @return The direction to draw the object in
	 */
	public Direction geDirection();
	
	/**
	 * 
	 * @return The position of the object on the GUI
	 */
	public Position getPosition();
	

}
