package de.stealmycode.beehive;

import de.stealmycode.beehive.graphics_engine.Window;
import de.stealmycode.beehive.model.map.Map;
import de.stealmycode.beehive.model.map.MapGenerator;
import de.stealmycode.beehive.model.world.AvailableProperties;
import de.stealmycode.beehive.model.world.FieldProperty;
import de.stealmycode.beehive.model.world.World;

public class Beehive {

	/**
	 * @param args
	 */
    public static void main(String[] args) {
        AvailableProperties.addProperty(new FieldProperty("Blume", 5f));
        AvailableProperties.addProperty(new FieldProperty("Baum", 5f));
        
        World world = new World(21, 18);
        world.generateWorld();

        MapGenerator gen = MapGenerator.getInstance();
        Map map = gen.generate(world);
        
        
        

        Window window = new Window();
        window.initialize();
        
        window.setStaticObjects(map.getDrawables());

        while(!window.isCloseRequested()) {
                window.render();

                try {
                        Thread.sleep(100);
                } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
        }

        window.closeWindow();
    }
}
