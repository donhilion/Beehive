package de.stealmycode.beehive.graphics_engine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import utils.Logger;

/**
 * Window class which encapsulates the lwjgl functionality.
 * 
 * @author donhilion
 *
 */
public class Window {
	/**
	 * Width of the window.
	 */
	private int width;
	/**
	 * Height of the window.
	 */
	private int height;
	
	private ImageManager imageManager;
	
	/**
	 * Creates a new instance of this class with standard values.
	 */
	public Window() {
		this(640, 480);
	}
	
	/**
	 * Creates a new instance of this class and sets the size of the window to the given
	 * width and size.
	 * 
	 * @param width The width of the window.
	 * @param height The height of the window.
	 */
	public Window(int width, int height) {
		this.width = width;
		this.height = height;
		Display.setTitle("Beehive");
	}
	
	/**
	 * Initializes the lwjgl framework.
	 * 
	 * @return true if the initialization was successful, else otherwise.
	 */
	public boolean initialize() {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create();
			
//			Renderer renderer = new Renderer();
//			renderer.init();
			
			imageManager = new ImageManager();
			imageManager.loadImage("ressources/sprite_test.png");
			imageManager.loadImageDescription("ressources/sprite_test_description.desc");
			return true;
		} catch (LWJGLException e) {
			Logger.loge("Could not set display mode.", e, this.getClass());
			return false;
		}
	}
	
	/**
	 * Determines if the window should be closed.
	 * 
	 * @return true if the window should be closed.
	 */
	public boolean isCloseRequested() {
		return Display.isCloseRequested();
	}
	
	/**
	 * Closes the window.
	 */
	public void closeWindow() {
		Display.destroy();
	}
	
	/**
	 * Renders the objects.
	 */
	public void render() {
		// TODO render images
		
		Display.update();
	}
	
}
