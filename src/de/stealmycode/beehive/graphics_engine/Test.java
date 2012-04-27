package de.stealmycode.beehive.graphics_engine;

/**
 * Class for testing the graphics engine.
 *
 * @author donhilion
 */
public class Test {
	public static void main(String[] args) {
		Window window = new Window();
		window.initialize();
		window.createWindow();
		
		while(!window.isCloseRequested()) {
			window.render();
		}
		
		window.closeWindow();
	}
}
