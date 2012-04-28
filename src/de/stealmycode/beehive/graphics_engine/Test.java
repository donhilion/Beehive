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
		
		while(!window.isCloseRequested()) {
			window.render();
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		window.closeWindow();
	}
}
