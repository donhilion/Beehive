package de.stealmycode.beehive.interfaces;

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
}
