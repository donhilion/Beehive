package de.stealmycode.beehive.model.world;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import de.stealmycode.beehive.model.world.animals.Critter;
import de.stealmycode.beehive.model.world.animals.IMovable;
import de.stealmycode.beehive.utils.Position;

public class World implements IWorld {
	private static float DEF_PROBABILITY = 0.1f;
	
	private int width = 200;
	private int height = 200;
	private Field[][] fields = null;
	private List<IMovable> movableList = null;
	
	public World(int width, int height) {
		this.width = width;
		this.height = height;
		fields = new Field[width][height];
		movableList = new LinkedList<IMovable>();
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

	@Override
	public void addMovableObject(IMovable movableObject) {		
		movableList.add(movableObject);
	}

	@Override
	public List<IMovable> getMovableList() {

		return movableList;
	}
   
}
