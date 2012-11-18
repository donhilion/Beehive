package de.stealmycode.beehive.game_engine;

import de.stealmycode.beehive.graphics_engine.KeyboardEvent;
import de.stealmycode.beehive.graphics_engine.Window;
import de.stealmycode.beehive.utils.Constants;

/**
 * The logical menu of the game.
 * 
 * @author donhilion
 * 
 */
public class Menu {
	/**
	 * The state machine of the program
	 */
	private ProgramStateMachine stateMachine;
	/**
	 * The window of the program.
	 */
	private Window window;

	/**
	 * The entries for the menu.
	 */
	private String[] menuElements = { "Start", "Ende" };
	/**
	 * The state related to the menu entry.
	 */
	private ProgramState[] statesOfElements = { ProgramState.GAME,
			ProgramState.STOP };
	/**
	 * The current selected entry.
	 */
	private int selected = 0;

	/**
	 * Creates a new instance of this class.
	 * 
	 * @param stateMachine
	 *            The state machine of the program.
	 * @param window
	 *            The window of the program.
	 */
	public Menu(ProgramStateMachine stateMachine, Window window) {
		this.stateMachine = stateMachine;
		this.window = window;

		window.setMenuEntries(menuElements);
		window.setSelectedMenuEntry(selected);
	}

	/**
	 * This method will be called every tick the program state is
	 * {@link ProgramState#MENU}
	 */
	public void tick() {
		checkKeyboard();
		window.render();
	}

	/**
	 * Checks the keyboard events.
	 */
	public void checkKeyboard() {
		KeyboardEvent ke = window.getNextKeyboardEvent();
		while (ke != null) {
			if (ke.isKeyDown()) {
				switch (ke.getKeyCode()) {
				case Constants.KEYCODE_DOWN:
					if (selected < menuElements.length - 1) {
						selected++;
						window.setSelectedMenuEntry(selected);
					}
					break;
				case Constants.KEYCODE_UP:
					if (selected > 0) {
						selected--;
						window.setSelectedMenuEntry(selected);
					}
					break;
				case Constants.KEYCODE_RETURN:
					stateMachine.changeState(statesOfElements[selected]);
					break;
				}
			}
			ke = window.getNextKeyboardEvent();
		}
	}
}
