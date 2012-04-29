package de.stealmycode.beehive.model.world;

import de.stealmycode.beehive.utils.Direction;
import de.stealmycode.beehive.utils.Position;

public abstract class AbstractDrawableObject implements IDrawable {

    private Direction   direction = Direction.NORTH;
    private Position    position = new Position(0, 0);
    
    public AbstractDrawableObject(Position position) {
       this.position = position;
    }
    
    @Override
    public abstract int getImageID();

    @Override
    public Direction getDirection() {
        return direction;
    }
    
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public Position getPosition() {
        return position;
    }
    
    public void setPosition(Position position) {
        this.position = position;
    }

}
