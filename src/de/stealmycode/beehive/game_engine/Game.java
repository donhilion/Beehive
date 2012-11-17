/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.stealmycode.beehive.game_engine;

import de.stealmycode.beehive.Beehive;
import de.stealmycode.beehive.graphics_engine.Window;
import de.stealmycode.beehive.model.map.Map;
import de.stealmycode.beehive.model.map.MapGenerator;
import de.stealmycode.beehive.model.world.*;
import de.stealmycode.beehive.model.world.animals.Bee;
import de.stealmycode.beehive.utils.Position;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.Map.Entry;

import org.newdawn.slick.Graphics;

import com.sun.xml.internal.bind.v2.util.CollisionCheckStack;

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
    private Window				window;

    /**
     * 
     * @param difficulty 
     */
    public Game(String difficulty, int width, int height, Window window) {
        this(difficulty, null, null, window);
        
        world = new World(width, height);
        world.generateWorld(availableProperties);
        map = MapGenerator.getInstance().generate(world);
        map.addDrawable(new Hive(new Position(width/2, height/2)));
        
        window.setStaticObjects(map.getDrawables());
        window.setDynamicObjects(world.getMovables());
        input = new Input(world, window);
        
        Bee bee = new Bee(new Position(3, 3));
        Bee bee1 = new Bee(new Position(3, 6));
        Bee bee2 = new Bee(new Position(5, 4));
        
        world.addMovableObject(bee); 
        world.addMovableObject(bee1); 
        world.addMovableObject(bee2);
    }
    
    public Game(String difficulty, World world, Map map, Window window) {
        this.difficulty = difficulty;
        availableProperties = new AvailableProperties();
        readFieldProperties();
        this.world = world;
        this.map = map;
        this.window = window;    
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

    public void tick() {
    	input.registerKeyEvent(window.getNextKeyboardEvent());
    	
    	world.moveMovables();
            
    	input.registerMouseEvent(window.getMouseInfo());
        window.render();
    }
}
