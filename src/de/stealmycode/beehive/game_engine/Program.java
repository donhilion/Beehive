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
	
	private ProgramState currentState;
	private Game game;
//	private Menu menu;
	private Window window;

	/*
	 * (non-Javadoc)
	 * @see de.stealmycode.beehive.game_engine.ProgramStateMachine#changeState(de.stealmycode.beehive.game_engine.ProgramState)
	 */
	@Override
	public void changeState(ProgramState newState) {
		currentState = newState;
		switch(currentState) {
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
	
	public Program(Window window) {
		this.window = window;
		game = new Game(Constants.CONFIG_MEDIUM, 21, 13, window);
		currentState = ProgramState.GAME;
	}
	
	public void start() {
		while(currentState != ProgramState.STOP) {
			switch(currentState) {
			case GAME:
				game.tick();
			default:
				break;
			}

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(window.isCloseRequested()) {
            	currentState = ProgramState.STOP;
            }
		}

        window.closeWindow();
	}

}
