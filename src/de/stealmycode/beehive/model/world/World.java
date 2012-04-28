package de.stealmycode.beehive.model.world;

public class World {
	private int width = 200;
	private int height = 200;
	private Field[][] map = null;
	
	public World(int width, int height) {
		map = new Field[width][height];
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	
}
