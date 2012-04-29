package de.stealmycode.beehive.game_engine;

import java.util.List;

import de.stealmycode.beehive.graphics_engine.MouseInfo;
import de.stealmycode.beehive.model.world.World;
import de.stealmycode.beehive.model.world.animals.IMovable;

public class Input {
	
	private World world;
	private boolean leftButtonPressed = false;

	
	public Input(World world)
	{
		this.world = world;
	}
	
	
	
	public void registerMouseEvent(MouseInfo mouseInfo)
	{
		if(mouseInfo.isLeftButtonDown() && !leftButtonPressed)
		{
			leftButtonPressed = true;
			System.out.println("Left Button Pressed -- X: " + mouseInfo.getX() + " --- Y: " + mouseInfo.getY());
			
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
			System.out.println("Left Button Released");
		}
	}

}
