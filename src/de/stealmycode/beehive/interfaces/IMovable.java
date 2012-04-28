package de.stealmycode.beehive.interfaces;

import de.stealmycode.beehive.model.world.Position;
import de.stealmycode.beehive.utils.Direction;

/**
 * Interface for all objects that has to be drawn to the
 * GUI
 * 
 * @author Shadowrunner
 *
 */
public interface IMovable {

	
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
	 * The progress-value represents if the object is still on his old position (0),
	 * on the way to a new position (between 0 and 1 exclusive)
	 * or arrived the new position (1)
	 * 
	 * @return a float-value between 0 and 1 inclusive.
	 */
	public float getProgress();
	
	/**
	 * 
	 * @return The position of the object on the GUI
	 */
	public Position getPosition();
	
}
