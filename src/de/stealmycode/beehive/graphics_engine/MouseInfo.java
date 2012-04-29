package de.stealmycode.beehive.graphics_engine;

/**
 * Contains the information of the mouse position and the button states.
 * 
 * @author donhilion
 *
 */
public class MouseInfo {
	private int x;
	private int y;
	private boolean left;
	private boolean middle;
	private boolean right;
	
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param x x part of the position.
	 * @param y y part of the position.
	 * @param left true if the left button is pressed
	 * @param middle true if the middle button is pressed
	 * @param right true if the right button is pressed
	 */
	public MouseInfo(int x, int y, boolean left, boolean middle, boolean right) {
		this.x = x;
		this.y = y;
		this.left = left;
		this.middle = middle;
		this.right = right;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public boolean isLeftButtonDown() {
		return left;
	}
	
	public boolean isMiddleButtonDown() {
		return middle;
	}
	
	public boolean isRightButtonDown() {
		return right;
	}

}
