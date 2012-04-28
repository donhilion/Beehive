package de.stealmycode.beehive.graphics_engine;

import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import de.stealmycode.beehive.interfaces.IDrawable;
import de.stealmycode.beehive.interfaces.IMovable;
import de.stealmycode.beehive.utils.Logger;


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
	private MeadowRenderer meadowRenderer;
	
	/**
	 * Creates a new instance of this class with standard values.
	 */
	public Window() {
		this(800, 600);
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
			
			meadowRenderer = new MeadowRenderer();
			meadowRenderer.init(width, height);
			
			imageManager = new ImageManager();
			imageManager.loadImage("ressources", "texture_test_description.desc");
			imageManager.loadImageDescription("ressources/sprite_test_description.desc");
			
			meadowRenderer.setImageRenderer(imageManager);
			
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
		meadowRenderer.draw();
		
		Display.update();
	}
	
	public void setStaticObjects(List<IDrawable> list) {
		if(meadowRenderer != null) {
			meadowRenderer.setStaticObjects(list);
		}
	}
	
	public void setDynamicObjects(List<IMovable> list) {
		if(meadowRenderer != null) {
			meadowRenderer.setDynamicObjects(list);
		}
	}
	
}
