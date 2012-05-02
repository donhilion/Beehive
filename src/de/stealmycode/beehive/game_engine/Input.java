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

public class Input {
	
	private World world;
	private boolean leftButtonPressed = false;
	private Window window;
	private List<AbstractMovableObject> selectedObjects;
	private int nextCommand = 0;

	
	public Input(World world, Window window)
	{
		this.world = world;
		this.window = window;
		
		selectedObjects = new LinkedList<AbstractMovableObject>();
	}
	
	
	
	private Position getCombPosition(int x, int y)
	{
		
		int xOfComb = (int) ((float) (x - 0.5f) / (float) (Constants.SIZE_OF_COMB * 0.75f));
		
		float multiplikator = 0;
		
		if(xOfComb % 2  != 0)
		{
			multiplikator = -Constants.SIZE_OF_COMB * Constants.SIN_60;
		}
//		float linkeSeite = y - 600;
//		float rechts1 = -Constants.SIZE_OF_COMB * Constants.SIN_60;
//		float rechts2 = xOfComb % 2;
//		float rechts3 = 1 + rechts2;
//		
//		float links2 = (linkeSeite / rechts1 * rechts3);
//		
//		int yOfComb = (int) (links2 - 0.5f) ;
		
//		int yOfComb = (int) ((y - 600) / - Constants.SIZE_OF_COMB * Constants.SIN_60 * (1 + (float)(xOfComb % 2)) - 0.5f) ;
		
		int yOfComb = (int) (((y - 600f)  - 0.5f * (1f + multiplikator)) /  (-Constants.SIZE_OF_COMB * Constants.SIN_60));
		
		return new Position(xOfComb, yOfComb);
	}
	
	public void registerMouseEvent(MouseInfo mouseInfo)
	{
		if(mouseInfo.isLeftButtonDown() && !leftButtonPressed)
		{
			leftButtonPressed = true;
//			Log.debug("Left Button Pressed -- X: " + mouseInfo.getX() + " --- Y: " + mouseInfo.getY());
			
			if(window != null)
			{
//				Position mousePosition = window.getGamePosition((int) mouseInfo.getX(), (int) mouseInfo.getY());
				
				Position mousePosition = getCombPosition((int) mouseInfo.getX(), (int) mouseInfo.getY());
//				Log.debug("Comb -- X: " + mousePosition.getX() + " --- Y: " + mousePosition.getY());
				
				/*
				 * is any movable object selected?
				 * if so, proceed the command
				 * 
				 * else unselect the objects and look if there is a new movable object to select
				 */
				if(selectedObjects.size() > 0 && nextCommand != 0)
				{
					setNewPositionForSelectedObjects(mousePosition);
					nextCommand = 0;
				}else
				{
					selectedObjects.clear();
					selectMovableObjectAtPosition(mousePosition);
				}
				
				
				
			}else
			{
//				Log.debug("Window not initiallized!");
			}

			
//			List<IMovable> list = world.getMovables();
//			
//			for(IMovable tempObject : list)
//			{
//				if(tempObject.getPosition().getX() == (int)  mouseInfo.getX() &&
//					tempObject.getPosition().getY() == (int) mouseInfo.getY())
//				{
//					
//				}
//			}
			
		}else if(!mouseInfo.isLeftButtonDown() && leftButtonPressed)
		{
			leftButtonPressed = false;
//			Log.debug("Left Button Released");
		}
	}
	
	
	private void selectMovableObjectAtPosition(Position position)
	{		
		for(IMovable object : world.getMovables())
		{
			if(object.getPosition().equals(position))
			{
				selectedObjects.add((AbstractMovableObject) object);
				
//				Log.warning("I found a bee =)");
			}
		}
		
		Log.warning(selectedObjects.size() + " Objects selected");
	}
	
	
	public void registerKeyEvent(KeyboardEvent kEvent)
	{
		if(kEvent == null) return;
		
		switch (kEvent.getKeyCode())
		{
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
	
	private void setNewPositionForSelectedObjects(Position position)
	{
		for(AbstractMovableObject object : selectedObjects)
		{
			object.move(world, position);
		}
	}

}
