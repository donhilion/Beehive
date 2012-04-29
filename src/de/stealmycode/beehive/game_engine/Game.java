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
import de.stealmycode.beehive.model.world.AvailableProperties;
import de.stealmycode.beehive.model.world.FieldProperty;
import de.stealmycode.beehive.model.world.Hive;
import de.stealmycode.beehive.model.world.IDrawable;
import de.stealmycode.beehive.model.world.World;
import de.stealmycode.beehive.model.world.animals.Bee;
import de.stealmycode.beehive.utils.Direction;
import de.stealmycode.beehive.utils.Position;
import java.util.Map.Entry;
import org.yaml.snakeyaml.scanner.Constant;

/**
 *
 * @author fate
 */
public class Game {
    
    private String difficulty;
    
    private AvailableProperties availableProperties;
    private World               world;
    private Map                 map;

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
        map.addDrawable(new IDrawable() {
        	
        	Position position = new Position(0, 0);
			
			@Override
			public Position getPosition() {
				return position;
			}
			
			@Override
			public int getImageID() {
				return 20;
			}
			
			@Override
			public Direction getDirection() {
				return Direction.SOUTH;
			}
		});
//        map.addDrawable(new IDrawable() {
//        	
//        	Position position = new Position(11, 10);
//			
//			@Override
//			public void setDirection(Direction direction) {
//				
//			}
//			
//			@Override
//			public Position getPosition() {
//				return position;
//			}
//			
//			@Override
//			public int getImageID() {
//				return 21;
//			}
//			
//			@Override
//			public Direction getDirection() {
//				return Direction.SOUTH;
//			}
//		});
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
//        Bee bee = new Bee(Direction.NORTH_EAST, 1, 1, 1, new Position(10, 9));
//        world.addMovableObject(bee);
        while(!window.isCloseRequested()) {
        	// v needed to enable scrolling v
        	KeyboardEvent kEvent;
        	do {
        		kEvent = window.getNextKeyboardEvent();
        	} while(kEvent != null);
        	window.getMouseInfo();
        	// ^ needed to enable scrolling ^
            window.render();
            try {
                Thread.sleep(100/6);

//                float progress = world.getMovables().get(0).getProgress();
//                world.getMovables().get(0).setProgress(progress + 0.1f);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        window.closeWindow();

    }
}
