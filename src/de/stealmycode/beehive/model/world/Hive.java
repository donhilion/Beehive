/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.stealmycode.beehive.model.world;

import de.stealmycode.beehive.utils.Direction;
import de.stealmycode.beehive.utils.Position;

/**
 *
 * @author fate
 */
public class Hive implements IDrawable {

    private Position position;

    public Hive(Position position) {
        this.position = position;
    }
    
    @Override
    public int getImageID() {
        return 8;
    }

    @Override
    public Direction getDirection() {
        return Direction.NORTH;
    }

    @Override
    public Position getPosition() {
        return position;
    }
    
}
