package de.stealmycode.beehive.model.world.animals;

import de.stealmycode.beehive.utils.Direction;
import de.stealmycode.beehive.utils.Position;

public class Critter implements IMovable {

	/**
	 * Variable that contains the directions the Critter is looking to
	 */
	protected Direction direction;
	
	/**
	 * ID for the image-representation of this Critter
	 */
	protected int imageID		= 0;
	
	/**
	 * Defines the strength of the Critters attack
	 */
	protected int attackValue 	= 1;
	
	/**
	 * Defines the strength of the Critters defense
	 */
	protected int defenceValue 	= 1;
	
	/**
	 * Defines if the Critter is moving.
	 * A value of 0 means that the Critter is not moving.
	 * A value between 0 exclusive and 1 exclusive means that the Critter is moving to an other position.
	 * A value of 1 means that the Critter has reached the new position.
	 */
	protected float progress	= 0f;
	
	/**
	 * Defines the current position of the Critter.
	 */
	protected Position position;
	
	/**
	 * If the Critter has to move to an other Position,
	 * thos variable contains it.
	 */
	protected Position newPosition;
	
	/**
	 * Standard-Constructor for a Critter.
	 * 
	 * @param direction Variable that contains the directions the Critter is looking to.
	 * @param imageID ID for the image-representation of this Critter
	 * @param attackValue Defines the strength of the Critters attack
	 * @param defenceValue Defines the strength of the Critters defense
	 * @param position Defines the current position of the Critter.
	 */
	public Critter(Direction direction,
				   int imageID,
				   int attackValue,
				   int defenceValue,
				   Position position)
	{
		this.direction = direction;
		
		this.imageID	  = imageID;
		this.attackValue  = attackValue;
		this.defenceValue = defenceValue;
		
		this.position 	  = position;
	}
	
	
	@Override
	public int getImageID() {
		
		return imageID;
	}

	@Override
	public Direction getDirection() {
		
		return direction;
	}

	@Override
	public Position getPosition() {
		
		return position;
	}

	@Override
	public float getProgress() {
		
		return progress;
	}
	
	@Override
	public void setProgress(float progress)
	{
		this.progress = progress;
	}

	@Override
	public void setNewPosition(Position newPosition) {
		
		this.newPosition = newPosition;
		
	}

	@Override
	public Position getNewPosition() {
		
		return newPosition;
	}

	@Override
	public void setCurrentPosition(Position position) {
		
		this.position = position;
		
	}

}
