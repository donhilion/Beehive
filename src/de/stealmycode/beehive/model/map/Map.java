/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.stealmycode.beehive.model.map;

import de.stealmycode.beehive.Beehive;
import de.stealmycode.beehive.model.world.Field;
import de.stealmycode.beehive.model.world.FieldProperty;
import de.stealmycode.beehive.model.world.Flower;
import de.stealmycode.beehive.model.world.IDrawable;
import de.stealmycode.beehive.utils.Direction;
import de.stealmycode.beehive.utils.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
//        final int iID = chooseImageID(field.getProperties());
        List<List<Integer>> whitelistList = new ArrayList<>();
        List<Integer> spriteIDs = null;
        Flower flower = null;
        for(FieldProperty fp : field.getProperties()) {
            whitelistList.add(Beehive.config.getWhitelistFor(fp.getName()));
            if (fp.getName().equals("flyable")) {
                drawables.add(new Flower(field.getPosition()));
                return ;
            }
        }
        
        if (whitelistList.size() > 0) {
            spriteIDs = new ArrayList<>(whitelistList.get(0)); // Beehive.graphicsConfig.getSpriteIDs();
            whitelistList.remove(0);

            for(List<Integer> list : whitelistList) {
                spriteIDs.retainAll(list);
            }
        }
        
        if (spriteIDs != null && spriteIDs.size() > 0) {
            final Random r = new Random();
            final int spriteID = (int) spriteIDs.get(
                    (spriteIDs.size() == 1 ? 0 :
                    r.nextInt(spriteIDs.size()-1))
                    );


            drawables.add(new IDrawable() {
                
                private int sID = spriteID;
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
