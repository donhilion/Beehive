package de.stealmycode.beehive.graphics_engine;

import java.util.List;

import de.stealmycode.beehive.model.world.animals.AbstractMovableObject;
import de.stealmycode.beehive.utils.Position;

public interface IRenderer {
	public void draw();
	
	public void scrollX(int x);
	public void scrollY(int y);
	
	public Position getGamePosition(int x, int y);
	public void setSelectedObjects(List<AbstractMovableObject> list);
}
