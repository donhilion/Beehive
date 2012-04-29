package de.stealmycode.beehive;

import de.stealmycode.beehive.config.Config;
import de.stealmycode.beehive.config.GraphicsConfig;
import de.stealmycode.beehive.game_engine.Game;
import de.stealmycode.beehive.graphics_engine.Window;
import de.stealmycode.beehive.utils.Constants;

public class Beehive {
    
    public static final Config          config          = new Config();
    public static final GraphicsConfig  graphicsConfig  = new GraphicsConfig();
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        loadConfigs();
        Window window = new Window();
        window.initialize();
        
        Game game = new Game(Constants.CONFIG_MEDIUM, 21, 13);
        game.start(window);
    }

    private static void loadConfigs() {
        graphicsConfig.load(Constants.PATH_GRAPHICS_CONFIG);
        config.load(Constants.PATH_CONFIG);
    }
}
