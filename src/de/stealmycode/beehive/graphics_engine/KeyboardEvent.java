package de.stealmycode.beehive.graphics_engine;

/**
 * Contains the information of a keyboard event.
 * 
 * @author donhilion
 *
 */
public class KeyboardEvent {
	/**
	 * The key code.
	 */
	private int keyCode;
	/**
	 * True if the key was pressed down.
	 */
	private boolean keyDown;
	
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param keyCode The key code of the event.
	 * @param keyDown True if the key was pressed down.
	 */
	public KeyboardEvent(int keyCode, boolean keyDown) {
		this.keyCode = keyCode;
		this.keyDown = keyDown;
	}
	
	/**
	 * Returns the key code of the event.
	 * 
	 * @return The key code of the event.
	 */
	public int getKeyCode() {
		return keyCode;
	}
	
	/**
	 * Returns true if the key was pressed down, false if it was released.
	 * 
	 * @return true if the key was pressed down, false if it was released.
	 */
	public boolean isKeyDown() {
		return keyDown;
	}
}
