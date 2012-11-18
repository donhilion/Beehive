package de.stealmycode.beehive.game_engine;

import java.util.LinkedList;
import java.util.List;

import de.stealmycode.beehive.graphics_engine.KeyboardEvent;
import de.stealmycode.beehive.graphics_engine.MouseInfo;
import de.stealmycode.beehive.graphics_engine.Window;
import de.stealmycode.beehive.model.world.World;
import de.stealmycode.beehive.model.world.animals.AbstractMovableObject;
import de.stealmycode.beehive.model.world.animals.IMovable;
import de.stealmycode.beehive.utils.Constants;
import de.stealmycode.beehive.utils.Log;
import de.stealmycode.beehive.utils.Position;

/**
 * This class is capable of handling user inputs.
 * 
 * @author donhilion
 * 
 */
public class Input {
	/**
	 * The {@link World} of the game.
	 */
	private World world;
	/**
	 * The last state of the left mouse button.
	 */
	private boolean leftButtonPressed = false;
	/**
	 * The {@link Window} of the game.
	 */
	private Window window;
	/**
	 * The list of the current selected objects.
	 */
	private List<AbstractMovableObject> selectedObjects;
	/**
	 * The list of the last mouse positions.
	 */
	private List<Position> mousePositions;
	/**
	 * The next command which should be executed on click.
	 */
	private int nextCommand = 0;

	/**
	 * Creates a new instance of this class.
	 * 
	 * @param world
	 *            The {@link World} of the game.
	 * @param window
	 *            The {@link Window} of the program.
	 */
	public Input(World world, Window window) {
		this.world = world;
		this.window = window;

		selectedObjects = new LinkedList<AbstractMovableObject>();
		mousePositions = new LinkedList<Position>();
	}

	/**
	 * Checks the current mouse state.
	 * 
	 * @param mouseInfo
	 *            The current mouse state.
	 */
	public void registerMouseEvent(MouseInfo mouseInfo) {
		if (mouseInfo.isLeftButtonDown() && !leftButtonPressed) {
			mousePositions.clear();

			leftButtonPressed = true;

			if (window != null) {
				Position mousePosition = window.getGamePosition(
						(int) mouseInfo.getX(), (int) mouseInfo.getY());

				/*
				 * is any movable object selected? if so, proceed the command
				 * 
				 * else unselect the objects and look if there is a new movable
				 * object to select
				 */

				if (mousePosition != null) {
					if (selectedObjects.size() > 0 && nextCommand != 0) {
						setNewPositionForSelectedObjects(mousePosition);
						nextCommand = 0;
						leftButtonPressed = false;
						return;
					}

					mousePositions.add(mousePosition);
				}

			} else {
				Log.debug("Window not initiallized!");
			}

		} else if (!mouseInfo.isLeftButtonDown() && leftButtonPressed) {
			leftButtonPressed = false;

			if (window != null) {
				boolean multiselection = true;

				Log.debug("Current List-Size: " + mousePositions.size());

				Position mousePosition = window.getGamePosition(
						(int) mouseInfo.getX(), (int) mouseInfo.getY());

				for (Position tempPosition : mousePositions) {
					if (tempPosition.equals(mousePosition)) {
						multiselection = false;
						break;
					}
				}

				if (multiselection && mousePosition != null) {
					mousePositions.add(mousePosition);
					handleMultiSelection();
				} else {
					handleSingleSelection();
				}

			}
		}
	}

	/**
	 * Selects a single object.
	 */
	private void handleSingleSelection() {
		selectedObjects.clear();

		if (mousePositions.size() > 0) {
			selectMovableObjectAtPosition(mousePositions.remove(0));
		}
	}

	/**
	 * Selects multiple objects.
	 */
	private void handleMultiSelection() {
		int minX = 0;
		int maxX = 0;

		int minY = 0;
		int maxY = 0;

		for (Position position : mousePositions) {
			minX = Math.min(minX, position.getX());
			maxX = Math.max(maxX, position.getX());

			minY = Math.min(minY, position.getY());
			maxY = Math.max(maxY, position.getY());
		}

		Log.debug("Minimum Position: " + minX + "|" + minY);
		Log.debug("Maximum Position: " + maxX + "|" + maxY);

		for (int x = minX; x <= maxX; x++) {
			for (int y = minY; y <= maxY; y++) {
				selectMovableObjectAtPosition(new Position(x, y));
			}
		}
	}

	/**
	 * Selects the movable object at the given position.
	 * 
	 * @param position
	 *            The position of the movable object.
	 */
	private void selectMovableObjectAtPosition(Position position) {
		for (IMovable object : world.getMovables()) {
			if (object.getPosition().equals(position)) {
				if (!selectedObjects.contains((AbstractMovableObject) object)) {
					selectedObjects.add((AbstractMovableObject) object);

					Log.debug("I found a bee =)");
				}
			}
		}

		Log.debug(selectedObjects.size() + " Objects selected");
	}

	/**
	 * Checks the last key event.
	 * 
	 * @param kEvent
	 *            The key event.
	 */
	public void registerKeyEvent(KeyboardEvent kEvent) {
		if (kEvent == null)
			return;

		switch (kEvent.getKeyCode()) {
		case Constants.KEYCODE_G:
			nextCommand = Constants.GO_TO_COMB;
			Log.warning("Klick on a comb to set new Position for the selected Objects...");

			break;

		case Constants.KEYCODE_ESC:
			selectedObjects.clear();
			nextCommand = 0;
			Log.warning("Selection cleared...");

			break;

		default:
			nextCommand = 0;
		}
	}

	/**
	 * Moves a movable object to the given position.
	 * 
	 * @param position
	 *            The position the movable object should move to.
	 */
	private void setNewPositionForSelectedObjects(Position position) {
		for (AbstractMovableObject object : selectedObjects) {
			object.move(world, position);
		}
	}

}
