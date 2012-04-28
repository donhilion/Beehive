/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.stealmycode.beehive.model.map;

import de.stealmycode.beehive.model.world.IDrawable;
import de.stealmycode.beehive.model.world.Field;
import de.stealmycode.beehive.utils.Direction;
import de.stealmycode.beehive.utils.Position;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fate
 */
public class Map{
    
    private List<IDrawable> drawables = null;

    public Map() {
        drawables = new ArrayList<IDrawable>();
    }
    
    public List<IDrawable> getDrawables() {
        return drawables;
    }
    
    public void addField(final Field field) {
        // Calculate some imageID depending on properties
//        final int iID = chooseImageID(field.getProperties());
        final int iID = 0;
        
        drawables.add(new IDrawable() {
            
            private int imageID = iID;
            private Direction direction = Direction.NORTH;
            private Position position = field.getPosition();

            @Override
            public int getImageID() {
                return imageID;
            }

            @Override
            public Direction getDirection() {
                return direction;
            }

            @Override
            public Position getPosition() {
                return position;
            }
        });
    }

    @Override
    public String toString() {
        return "asd";
//        return StringUtils.join(new String[] {"Hello", "World", "!"}, ", ");
    }
    
    
}
