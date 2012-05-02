package de.stealmycode.beehive.model.world;

import de.stealmycode.beehive.model.world.animals.AbstractMovableObject;
import de.stealmycode.beehive.model.world.animals.IMovable;
import de.stealmycode.beehive.utils.Direction;
import de.stealmycode.beehive.utils.Position;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class World {

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

    /**
     * 
     * @return
     */
    public Field[][] getFields() {
        return fields;
    }
    
    /**
     * 
     * @param pos
     * @return
     */
    public Field getField(Position pos) {
    	return fields[pos.getX()][pos.getY()];
    }

    /**
     * 
     * @param movableObject
     */
    public void addMovableObject(IMovable movableObject) {
        movables.add(movableObject);
    }

    /**
     * 
     * @return
     */
    public List<IMovable> getMovables() {
        return movables;
    }
    
    /**
     * Get the comb which is placed next to the specified field in the given
     * direction. If the x coordinates will change there are two rows to choose 
     * from cause of the zigzag comb rows.
     * 
     * @param field field used as origin
     * @param direction where to look for the neighbour
     * @return null if there is no neighbour, the neighbour otherwise
     */
    public Field getNeighbourField(Field field, Direction direction) {
    	Position pos		= field.getPosition();
    	Position newPos		= null;
    	switch (direction) {
            case NORTH:
                newPos = new Position(pos.getX(), pos.getY() - 1);
                break;
            case NORTH_EAST:
                newPos = new Position(pos.getX() + 1, pos.getY() - (pos.getX() + 1)
                        % 2);
                break;
            case NORTH_WEST:
                newPos = new Position(pos.getX() - 1, pos.getY() - (pos.getX() + 1) % 2);
                break;
    		case SOUTH:
    			newPos = new Position(pos.getX(), pos.getY()+1);
    			break;
    		case SOUTH_EAST:
    			newPos = new Position(pos.getX() + 1, pos.getY()+(pos.getX()%2));
    			break;
    		case SOUTH_WEST:
    			newPos = new Position(pos.getX() - 1, pos.getY()+(pos.getX()%2));
    			break;
    	}
    	if (newPos.getX() < 0 || newPos.getX() >= width ||
    		newPos.getY() < 0 || newPos.getY() >= height) {
    		return null;
    	}
    	return getField(newPos);
    }
    
    public List<Field> getNeighbourFields(Field field) {
        List<Field> result = new ArrayList<Field>(6);
        Field neighbour = null;
        if ((neighbour = getNeighbourField(field, Direction.NORTH)) != null) result.add(neighbour);
        if ((neighbour = getNeighbourField(field, Direction.NORTH_EAST)) != null) result.add(neighbour);
        if ((neighbour = getNeighbourField(field, Direction.SOUTH_EAST)) != null) result.add(neighbour);
        if ((neighbour = getNeighbourField(field, Direction.SOUTH)) != null) result.add(neighbour);
        if ((neighbour = getNeighbourField(field, Direction.SOUTH_WEST)) != null) result.add(neighbour);
        if ((neighbour = getNeighbourField(field, Direction.NORTH_WEST)) != null) result.add(neighbour);
        return result;
    }
    
    
    public void moveMovables()
    {
    	if(movables != null && movables.size() > 0)
    	{
    		for(IMovable object : movables)
    		{
    			((AbstractMovableObject) object).step();
    		}
    	}
    }
}
