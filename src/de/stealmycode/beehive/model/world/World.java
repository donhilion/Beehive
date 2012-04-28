package de.stealmycode.beehive.model.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.stealmycode.beehive.model.world.animals.Critter;
import de.stealmycode.beehive.utils.Position;

public class World implements IWorld {
	private static float DEF_PROBABILITY = 0.1f;
	
	private int width = 200;
	private int height = 200;
	private Field[][] fields = null;
	private List<Critter> critter = null;
	
	public World(int width, int height) {
		this.width = width;
		this.height = height;
		fields = new Field[width][height];
		critter = new ArrayList<Critter>();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public void generateWorld() {
		Random r = new Random();
		List<FieldProperty> properties = AvailableProperties.getProperties();
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				fields[x][y] = new Field(new Position(x, y));

				for (FieldProperty property : properties) {
					if (r.nextFloat() <= DEF_PROBABILITY * property.getProbability()) {
						fields[x][y].addProperty(property.clone());
					}
				}
			}
		}
	}

    @Override
    public Field[][] getFields() {
        return fields;
    }
}
