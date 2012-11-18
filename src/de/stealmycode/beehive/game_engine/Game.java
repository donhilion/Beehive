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

import java.util.Map.Entry;

/**
 * This class controls the game relevant actions.
 * 
 * @author fate
 */
public class Game {

	/**
	 * The difficulty of the game.
	 */
	private String difficulty;

	/**
	 * The settings of the current difficulty.
	 */
	private AvailableProperties availableProperties;
	/**
	 * The {@link World} of the game.
	 */
	private World world;
	/**
	 * The {@link Map} of the game.
	 */
	private Map map;
	/**
	 * The {@link Input} to handle user inputs.
	 */
	private Input input;
	/**
	 * The {@link Window} of the program the game runs in.
	 */
	private Window window;

	/**
	 * Creates a new instance of this class and sets some fields. Furthermore a
	 * {@link World} and {@link Map} will be created.
	 * 
	 * @param difficulty
	 *            The difficulty of the game.
	 * @param width
	 *            The width of the window.
	 * @param height
	 *            The height of the window.
	 * @param window
	 *            The {@link Window} of the program.
	 */
	public Game(String difficulty, int width, int height, Window window) {
		this(difficulty, null, null, window);

		world = new World(width, height);
		world.generateWorld(availableProperties);
		map = MapGenerator.getInstance().generate(world);
		map.addDrawable(new Hive(new Position(width / 2, height / 2)));

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

	/**
	 * Creates a new instance of this class and sets some fields. Furthermore
	 * the config will be loaded.
	 * 
	 * @param difficulty
	 *            The difficulty of the game.
	 * @param world
	 *            The {@link World} to use.
	 * @param map
	 *            The {@link Map} to use.
	 * @param window
	 *            The {@link Window} of the program.
	 */
	public Game(String difficulty, World world, Map map, Window window) {
		this.difficulty = difficulty;
		availableProperties = new AvailableProperties();
		readFieldProperties();
		this.world = world;
		this.map = map;
		this.window = window;
	}

	/**
	 * This method reads the config of the given difficulty level.
	 */
	private void readFieldProperties() {
		for (Entry<String, Float> entry : Beehive.config.getFieldProperties(
				difficulty).entrySet()) {
			// Might change this ugly cast
			availableProperties.addProperty(new FieldProperty(entry.getKey(),
					((Number) entry.getValue()).floatValue()));
		}
	}

	/**
	 * This method will be called every tick the program state is
	 * {@link ProgramState#GAME}.
	 */
	public void tick() {
		input.registerKeyEvent(window.getNextKeyboardEvent());

		world.moveMovables();

		input.registerMouseEvent(window.getMouseInfo());
		window.render();
	}
}
