/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.stealmycode.beehive.game_engine;

import de.stealmycode.beehive.Beehive;
import de.stealmycode.beehive.graphics_engine.KeyboardEvent;
import de.stealmycode.beehive.graphics_engine.Window;
import de.stealmycode.beehive.model.map.Map;
import de.stealmycode.beehive.model.map.MapGenerator;
import de.stealmycode.beehive.model.world.*;
import de.stealmycode.beehive.model.world.animals.Bee;
import de.stealmycode.beehive.utils.Direction;
import de.stealmycode.beehive.utils.Position;
import java.util.Map.Entry;

/**
 *
 * @author fate
 */
public class Game {
    
    private String difficulty;
    
    private AvailableProperties availableProperties;
    private World               world;
    private Map                 map;
    private Input				input;

    /**
     * 
     * @param difficulty 
     */
    public Game(String difficulty, int width, int height) {
        this(difficulty, null, null);
        
        world = new World(width, height);
        world.generateWorld(availableProperties);
        map = MapGenerator.getInstance().generate(world);
        map.addDrawable(new Hive(new Position(width/2, height/2)));
//        drawNudePics();
    }
    
    public Game(String difficulty, World world, Map map) {
        this.difficulty = difficulty;
        availableProperties = new AvailableProperties();
        readFieldProperties();
        this.world = world;
        this.map = map;
    }
    
    /**
     * 
     */
    private void readFieldProperties() {
        for (Entry<String, Float> entry : Beehive.config.getFieldProperties(difficulty).entrySet()) {
            // Might change this ugly cast
            availableProperties.addProperty(new FieldProperty(entry.getKey(), ((Number)entry.getValue()).floatValue()));
        }
    }

    public void start(Window window) {
        window.setStaticObjects(map.getDrawables());
        window.setDynamicObjects(world.getMovables());
        input = new Input(world, window);
//        Bee bee = new Bee(Direction.NORTH_EAST, 1, 1, 1, new Position(10, 9));
//        world.addMovableObject(bee);
        Bee bee = new Bee(new Position(3, 3));
        Bee bee1 = new Bee(new Position(3, 6));
        Bee bee2 = new Bee(new Position(5, 4));
        
        world.addMovableObject(bee); 
        world.addMovableObject(bee1); 
        world.addMovableObject(bee2);    
//        bee.move(world, world.getField(new Position(19, 12)));
        while(!window.isCloseRequested()) 
        {
        	// v needed to enable scrolling v
 		
	    	 input.registerKeyEvent(window.getNextKeyboardEvent());


//                bee.step();
        	
        	world.moveMovables();
                
        	input.registerMouseEvent(window.getMouseInfo());
        	// ^ needed to enable scrolling ^
            window.render();
            try {
                Thread.sleep(100);

//                float progress = world.getMovables().get(0).getProgress();
//                world.getMovables().get(0).setProgress(progress + 0.1f);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        window.closeWindow();

    }

    private void drawNudePics() {
        for(int i = 1;i < 37; i++) {
            final int j = i;
            map.addDrawable(new IDrawable() {

                @Override
                public int getImageID() {
                    return j;
                }

                @Override
                public Direction getDirection() {
                    return Direction.EAST;
                }

                @Override
                public Position getPosition() {
                    return new Position(j % 20, j < 20 ? 1 : 3);
                }
            });
        }
    }
}
