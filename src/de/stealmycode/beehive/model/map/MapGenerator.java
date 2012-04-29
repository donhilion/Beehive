/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.stealmycode.beehive.model.map;

import de.stealmycode.beehive.model.world.Field;
import de.stealmycode.beehive.model.world.World;

/**
 *
 * @author fate
 */
public class MapGenerator implements IMapGenerator {
    
    private static MapGenerator instance = null;
    
    private MapGenerator() {
    }
    
    public static MapGenerator getInstance() {
        if (instance == null) {
            instance = new MapGenerator();
        }
        return instance;
    }
    
    @Override
    public Map generate(World world) {
        Map map = new Map();
        Field[][] fields = world.getFields();
        
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[0].length; j++) {
//                map.addField(fields[i][j]);
            }
        }
        
        return map;
    }
}
