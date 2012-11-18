package de.stealmycode.beehive;

import de.stealmycode.beehive.config.Config;
import de.stealmycode.beehive.config.GraphicsConfig;
import de.stealmycode.beehive.game_engine.Program;
import de.stealmycode.beehive.graphics_engine.Window;
import de.stealmycode.beehive.utils.Constants;
import de.stealmycode.beehive.utils.Log;

/**
 * The main class of the program.
 * 
 * @author donhilion
 * 
 */
public class Beehive {

	/**
	 * The {@link Config} of the program.
	 */
	public static final Config config = new Config();
	/**
	 * The {@link GraphicsConfig} of the program.
	 */
	public static final GraphicsConfig graphicsConfig = new GraphicsConfig();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Log.preInit();
		loadConfigs();
		Log.init();
		Window window = new Window();
		window.initialize();

		Program program = new Program(window);
		program.start();
	}

	/**
	 * Loads the config files.
	 */
	private static void loadConfigs() {
		graphicsConfig.load(Constants.PATH_GRAPHICS_CONFIG);
		config.load(Constants.PATH_CONFIG);
	}
}
