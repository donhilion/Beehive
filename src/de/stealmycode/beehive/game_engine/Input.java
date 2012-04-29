package de.stealmycode.beehive.game_engine;

import java.util.List;

import de.stealmycode.beehive.graphics_engine.MouseInfo;
import de.stealmycode.beehive.model.world.World;
import de.stealmycode.beehive.model.world.animals.IMovable;
import de.stealmycode.beehive.utils.Constants;
import de.stealmycode.beehive.utils.Position;
import de.stealmycode.beehive.utils.Log;

public class Input {
	
	private World world;
	private boolean leftButtonPressed = false;

	
	public Input(World world)
	{
		this.world = world;
	}
	
	
	private Position getCombPosition(float x, float y)
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
			Log.debug("Left Button Pressed -- X: " + mouseInfo.getX() + " --- Y: " + mouseInfo.getY());
			
			Position mousePosition = getCombPosition(mouseInfo.getX(), mouseInfo.getY());
			Log.debug("Comb -- X: " + mousePosition.getX() + " --- Y: " + mousePosition.getY());


			
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
			Log.debug("Left Button Released");
		}
	}

}
