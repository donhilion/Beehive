package de.stealmycode.beehive.game_engine;

/**
 * Represents a program state machine.
 * 
 * @author donhilion
 * 
 */
public interface ProgramStateMachine {
	/**
	 * Changes the current state of this state machine.
	 * 
	 * @param newState
	 */
	public void changeState(ProgramState newState);
}
