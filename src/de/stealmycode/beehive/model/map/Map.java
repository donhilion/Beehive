/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.stealmycode.beehive.model.map;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import de.stealmycode.beehive.Beehive;
import de.stealmycode.beehive.model.world.Field;
import de.stealmycode.beehive.model.world.FieldProperty;
import de.stealmycode.beehive.model.world.IDrawable;
import de.stealmycode.beehive.utils.Direction;
import de.stealmycode.beehive.utils.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author fate
 */
public class Map{
    
    private List<IDrawable> drawables = null;

    public Map() {
        drawables = new ArrayList<IDrawable>();
    }
    
    public void addDrawable(IDrawable drawable) {
        drawables.add(drawable);
    }
    
    public List<IDrawable> getDrawables() {
        return drawables;
    }
    
    public void addField(final Field field) {
        // Calculate some imageID depending on properties
//        final int iID = chooseImageID(field.getProperties());
        final List<Integer> spriteIDs = Beehive.graphicsConfig.getSpriteIDs();
        for(FieldProperty fp : field.getProperties()) {
            List <Integer> fieldBlacklist = Beehive.config.getBlacklistFor(fp.getName());
            for(Integer spriteID : fieldBlacklist) {
                if (spriteIDs.contains(new Integer(spriteID))) {
                    spriteIDs.remove(new Integer(spriteID));
                }
            }
        }
        
//        System.out.println("LOL");

        if (spriteIDs.toArray().length > 0) {
            final int spriteID = (int) spriteIDs.toArray()[0];
            final Random r = new Random();

            drawables.add(new IDrawable() {
                
                private int sID = (int) spriteIDs.toArray()[r.nextInt(spriteIDs.toArray().length-1)];
//                private Direction direction = Direction.NORTH;
                private Position position = new Position(field.getPosition().getX(), field.getPosition().getY());
                
                @Override
                public int getImageID() {
                    return  sID;
                }

                @Override
                public Direction getDirection() {
                    return Direction.NORTH;
                }

                @Override
                public Position getPosition() {
                    return position;
                }
            });
            
//            System.out.println("LOL");

        }
    }
}
