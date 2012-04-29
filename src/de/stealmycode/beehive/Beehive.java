package de.stealmycode.beehive;

import de.stealmycode.beehive.config.Config;
import de.stealmycode.beehive.config.GraphicsConfig;
import de.stealmycode.beehive.graphics_engine.Window;
import de.stealmycode.beehive.model.map.Map;
import de.stealmycode.beehive.model.map.MapGenerator;
import de.stealmycode.beehive.model.world.AvailableProperties;
import de.stealmycode.beehive.model.world.FieldProperty;
import de.stealmycode.beehive.model.world.World;
import de.stealmycode.beehive.model.world.animals.Bee;
import de.stealmycode.beehive.utils.Constants;
import de.stealmycode.beehive.utils.Direction;
import de.stealmycode.beehive.utils.Position;

public class Beehive {
    
    public static final Config          config          = new Config();
    public static final GraphicsConfig  graphicsConfig  = new GraphicsConfig();
    /**
     * @param args
     */
    public static void main(String[] args) {
        loadConfigs();        
        AvailableProperties.addProperty(new FieldProperty("Blume", 5f));
        AvailableProperties.addProperty(new FieldProperty("Baum", 5f));

        World world = new World(21, 18);
        world.generateWorld();

        MapGenerator gen = MapGenerator.getInstance();
        Map map = gen.generate(world);
        Bee bee = new Bee(Direction.NORTH_EAST, 1, 1, 1, new Position(10, 9));
        world.addMovableObject(bee);


        Window window = new Window();
        window.initialize();

        window.setStaticObjects(map.getDrawables());

        window.setDynamicObjects(world.getMovableList());
        
        
		while(!window.isCloseRequested()) {
			window.render();

			try {
				Thread.sleep(100);

				float progress = world.getMovableList().get(0).getProgress();
				world.getMovableList().get(0).setProgress(progress + 0.1f);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		window.closeWindow();
    }

    private static void loadConfigs() {
        graphicsConfig.load(Constants.PATH_GRAPHICS_CONFIG);
        config.load(Constants.PATH_CONFIG);
    }
}
