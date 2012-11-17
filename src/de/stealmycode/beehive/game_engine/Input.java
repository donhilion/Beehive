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
	private List<Position> mousePositions;
	private int nextCommand = 0;

	
	public Input(World world, Window window)
	{
		this.world = world;
		this.window = window;
		
		selectedObjects = new LinkedList<AbstractMovableObject>();
		mousePositions = new LinkedList<Position>();
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
			mousePositions.clear();
			
			leftButtonPressed = true;
			
			if(window != null)
			{
				Position mousePosition = window.getGamePosition((int) mouseInfo.getX(), (int) mouseInfo.getY());
				
				/*
				 * is any movable object selected?
				 * if so, proceed the command
				 * 
				 * else unselect the objects and look if there is a new movable object to select
				 */
				
				if(mousePosition != null)
				{
					if(selectedObjects.size() > 0 && nextCommand != 0)
					{
						setNewPositionForSelectedObjects(mousePosition);
						nextCommand = 0;
						leftButtonPressed = false;
						return;
					}
					
					mousePositions.add(mousePosition);
				}
				
			}else
			{
//				Log.debug("Window not initiallized!");
			}

			
		}else if(!mouseInfo.isLeftButtonDown() && leftButtonPressed)
		{
			leftButtonPressed = false;
			
			if(window != null)
			{
				boolean multiselection = true;
				
				Log.debug("Current List-Size: " + mousePositions.size());
				
				Position mousePosition = window.getGamePosition((int) mouseInfo.getX(), (int) mouseInfo.getY());
				
				for(Position tempPosition : mousePositions)
				{
					if(tempPosition.equals(mousePosition))
					{
						multiselection = false;
						break;
					}
				}
				
				if(multiselection && mousePosition != null)
				{
					mousePositions.add(mousePosition);
					handleMultiSelection();
				}else
				{
					handleSingleSelection();
				}

			}
		}
	}
	
	private void handleSingleSelection()
	{
		selectedObjects.clear();
		
		if(mousePositions.size() > 0)
		{
			selectMovableObjectAtPosition(mousePositions.remove(0));
		}
	}
	
	private void handleMultiSelection()
	{
		int minX = 0;
		int maxX = 0;
		
		int minY = 0;
		int maxY = 0;
		
		for(Position position : mousePositions)
		{
			minX = Math.min(minX, position.getX());
			maxX = Math.max(maxX, position.getX());
			
			minY = Math.min(minY, position.getY());
			maxY = Math.max(maxY, position.getY());
		}
		
		Log.debug("Minimum Position: " + minX + "|" + minY);
		Log.debug("Maximum Position: " + maxX + "|" + maxY);
		
		for(int x = minX ; x <= maxX ; x++)
		{
			for(int y = minY ; y <= maxY ; y++)
			{
				selectMovableObjectAtPosition(new Position(x, y));
			}
		}
	}
	
	
	private void selectMovableObjectAtPosition(Position position)
	{		
		for(IMovable object : world.getMovables())
		{
			if(object.getPosition().equals(position))
			{			
				if(!selectedObjects.contains((AbstractMovableObject) object))
				{
					selectedObjects.add((AbstractMovableObject) object);
					
					Log.debug("I found a bee =)");
				}
			}
		}
		
		Log.debug(selectedObjects.size() + " Objects selected");
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
