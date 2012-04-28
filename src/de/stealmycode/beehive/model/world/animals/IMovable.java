package de.stealmycode.beehive.model.world.animals;

import de.stealmycode.beehive.model.world.IDrawable;
import de.stealmycode.beehive.utils.Position;

/**
 * Interface for all objects that has to be drawn to the
 * GUI
 * 
 * @author Shadowrunner
 *
 */
public interface IMovable extends IDrawable {

	/**
	 * The progress-value represents if the object is still on his old position (0),
	 * on the way to a new position (between 0 and 1 exclusive)
	 * or arrived the new position (1)
	 * 
	 * @return a float-value between 0 and 1 inclusive.
	 */
	public float getProgress();	
	
	public void setProgress(float progress);
	
	/**
	 * Method to set a position where the Object has to move to 
	 * 
	 * @param newPosition Contains the new position of the Object
	 */
	public void setNewPosition(Position newPosition);
	
	/**
	 * 
	 * @return The position where the Object has to move to. Can be NULL if no new position is set.
	 */
	public Position getNewPosition();
	
	/**
	 * If the Object has moved to an other position, this method has to be called to set the new Position.
	 * 
	 * @param position
	 */
	public void setCurrentPosition(Position position);
	
}
