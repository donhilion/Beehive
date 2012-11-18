package de.stealmycode.beehive.game_engine;

import de.stealmycode.beehive.graphics_engine.Window;
import de.stealmycode.beehive.graphics_engine.WindowState;
import de.stealmycode.beehive.utils.Constants;

/**
 * This class contains the main loop of the program
 * 
 * @author donhilion
 * 
 */
public class Program implements ProgramStateMachine {
	/**
	 * The current {@link ProgramState}.
	 */
	private ProgramState currentState;
	/**
	 * The current {@link Game} of the program.
	 */
	private Game game;
	private Menu menu;
	/**
	 * The {@link Window} of the program.
	 */
	private Window window;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.stealmycode.beehive.game_engine.ProgramStateMachine#changeState(de
	 * .stealmycode.beehive.game_engine.ProgramState)
	 */
	@Override
	public void changeState(ProgramState newState) {
		currentState = newState;
		switch (currentState) {
		case MENU:
			window.changeState(WindowState.MENU);
			break;
		case GAME:
			window.changeState(WindowState.MEADOW);
			break;
		default:
			break;
		}
	}

	/**
	 * Creates a new instance of this class.
	 * 
	 * @param window
	 *            The {@link Window} which should be used.
	 */
	public Program(Window window) {
		this.window = window;
		game = new Game(Constants.CONFIG_MEDIUM, 21, 13, window, this);
		menu = new Menu(this, window);
		currentState = ProgramState.MENU;
	}

	/**
	 * Starts the program. This method contains the main loop which will call
	 * the tick method of the current state.
	 */
	public void start() {
		while (currentState != ProgramState.STOP) {
			switch (currentState) {
			case GAME:
				game.tick();
				break;
			case MENU:
				menu.tick();
				break;
			default:
				break;
			}

			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (window.isCloseRequested()) {
				currentState = ProgramState.STOP;
			}
		}

		window.closeWindow();
	}

}
