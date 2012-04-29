package de.stealmycode.beehive.utils;

public class Position {
	private int x;
	private int y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	/**
	 * 
	 * @param pos
	 * @return
	 */
	private boolean equals(Position pos) {
	    if (pos.x != x) return false;
	    if (pos.y != y) return false;
	    return true;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (obj instanceof Position)
	        return equals((Position)obj);
	    return false;
	}
}
