package de.stealmycode.beehive.graphics_engine;

import de.stealmycode.beehive.utils.Position;

public interface IRenderer {
	public void draw();
	
	public void scrollX(int x);
	public void scrollY(int y);
	
	public Position getGamePosition(int x, int y);
}
