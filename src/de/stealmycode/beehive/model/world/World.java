package de.stealmycode.beehive.model.world;

import java.util.Random;

import de.stealmycode.beehive.utils.Position;

public class World {
	private static float DEF_PROBABILITY = 0.1f;
	
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
	
	public void generateWorld() {
		Random r = new Random();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				map[x][y] = new Field(new Position(x, y));
				for (FieldProperty property : AvailableProperties.getProperties()) {
					if (r.nextFloat() <= DEF_PROBABILITY * property.probability) {
						map[x][y].addProperty(property.clone());
					}
				}
			}
		}
	}
}
