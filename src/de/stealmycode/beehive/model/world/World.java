package de.stealmycode.beehive.model.world;

import de.stealmycode.beehive.model.world.animals.IMovable;
import de.stealmycode.beehive.utils.Position;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class World implements IWorld {

    private static float DEF_PROBABILITY = 0.1f;
    private AvailableProperties availableProperties;
    private int width = 200;
    private int height = 200;
    private Field[][] fields = null;
    private List<IMovable> movables = null;

    /**
     * 
     * @param width
     * @param height 
     */
    public World(int width, int height) {
        this.width = width;
        this.height = height;
        fields = new Field[width][height];
        movables = new LinkedList<>();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * 
     * @param availableProperties 
     */
    public void generateWorld(AvailableProperties availableProperties) {
        this.availableProperties = availableProperties;
        Random r = new Random();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                fields[x][y] = new Field(new Position(x, y));
                for (FieldProperty property : this.availableProperties.getProperties()) {
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
        movables.add(movableObject);
    }

    @Override
    public List<IMovable> getMovables() {

        return movables;
    }
}
