package de.stealmycode.beehive.graphics_engine;

import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import de.stealmycode.beehive.model.world.IDrawable;
import de.stealmycode.beehive.model.world.animals.IMovable;
import de.stealmycode.beehive.utils.Constants;
import de.stealmycode.beehive.utils.Log;
import de.stealmycode.beehive.utils.Position;

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

	/**
	 * The {@link ImageManager} to get the sprites.
	 */
	private ImageManager imageManager;
	/**
	 * The {@link IRenderer} for rendering meadows.
	 */
	private MeadowRenderer meadowRenderer;
	private MenuRenderer menuRenderer;
	

	/**
	 * The active {@link IRenderer}.
	 */
	private IRenderer currentRenderer;
	/**
	 * The current {@link WindowState}.
	 */
	private WindowState currentState;

	/**
	 * Creates a new instance of this class with standard values.
	 */
	public Window() {
		this(800, 600);
	}

	/**
	 * Creates a new instance of this class and sets the size of the window to
	 * the given width and size.
	 * 
	 * @param width
	 *            The width of the window.
	 * @param height
	 *            The height of the window.
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
			Display.setVSyncEnabled(true); // seems not to work

			meadowRenderer = new MeadowRenderer();
			meadowRenderer.init(width, height);

			imageManager = new ImageManager();
			imageManager.loadConfig("config/graphics.yml");
			
			menuRenderer = new MenuRenderer();
			menuRenderer.init(width,height);
			
			menuRenderer.setImageRenderer(imageManager);
			meadowRenderer.setImageRenderer(imageManager);
			currentRenderer = menuRenderer;
			
			return true;
		} catch (LWJGLException e) {
			Log.error("Could not set display mode.");
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

		if (currentRenderer != null) {
			currentRenderer.draw();
		}

		Display.update();
		Display.sync(60); // seems not to work
	}

	/**
	 * Sets the static objects.
	 * 
	 * @param list
	 */
	public void setStaticObjects(List<IDrawable> list) {
		if (meadowRenderer != null) {
			meadowRenderer.setStaticObjects(list);
		}
	}

	/**
	 * Sets the dynamic objects.
	 * 
	 * @param list
	 */
	public void setDynamicObjects(List<IMovable> list) {
		if (meadowRenderer != null) {
			meadowRenderer.setDynamicObjects(list);
		}
	}

	/**
	 * Changes the current {@link WindowState}.
	 * 
	 * @param newState
	 */
	public void changeState(WindowState newState) {
		currentState = newState;
		switch (currentState) {
		case MEADOW:
			currentRenderer = meadowRenderer;
			break;
		case MENU:
			currentRenderer = menuRenderer;
		default:
			break;
		}
	}

	/**
	 * Returns the next keyboard event. If there is no event in the queue, null
	 * is returned. If a key is pressed with no constant, -1 is set as key code.
	 * 
	 * @return the next keyboard event or null.
	 */
	public KeyboardEvent getNextKeyboardEvent() {
		Keyboard.poll();
		if (Keyboard.next()) {
			switch (Keyboard.getEventKey()) {
			case Keyboard.KEY_0:
				return new KeyboardEvent(Constants.KEYCODE_0,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_1:
				return new KeyboardEvent(Constants.KEYCODE_1,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_2:
				return new KeyboardEvent(Constants.KEYCODE_2,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_3:
				return new KeyboardEvent(Constants.KEYCODE_3,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_4:
				return new KeyboardEvent(Constants.KEYCODE_4,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_5:
				return new KeyboardEvent(Constants.KEYCODE_5,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_6:
				return new KeyboardEvent(Constants.KEYCODE_6,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_7:
				return new KeyboardEvent(Constants.KEYCODE_7,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_8:
				return new KeyboardEvent(Constants.KEYCODE_8,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_9:
				return new KeyboardEvent(Constants.KEYCODE_9,
						Keyboard.getEventKeyState());

			case Keyboard.KEY_A:
				return new KeyboardEvent(Constants.KEYCODE_A,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_B:
				return new KeyboardEvent(Constants.KEYCODE_B,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_C:
				return new KeyboardEvent(Constants.KEYCODE_C,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_D:
				return new KeyboardEvent(Constants.KEYCODE_D,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_E:
				return new KeyboardEvent(Constants.KEYCODE_E,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_F:
				return new KeyboardEvent(Constants.KEYCODE_F,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_G:
				return new KeyboardEvent(Constants.KEYCODE_G,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_H:
				return new KeyboardEvent(Constants.KEYCODE_H,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_I:
				return new KeyboardEvent(Constants.KEYCODE_I,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_J:
				return new KeyboardEvent(Constants.KEYCODE_J,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_K:
				return new KeyboardEvent(Constants.KEYCODE_K,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_L:
				return new KeyboardEvent(Constants.KEYCODE_L,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_M:
				return new KeyboardEvent(Constants.KEYCODE_M,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_N:
				return new KeyboardEvent(Constants.KEYCODE_N,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_O:
				return new KeyboardEvent(Constants.KEYCODE_O,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_P:
				return new KeyboardEvent(Constants.KEYCODE_P,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_Q:
				return new KeyboardEvent(Constants.KEYCODE_Q,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_R:
				return new KeyboardEvent(Constants.KEYCODE_R,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_S:
				return new KeyboardEvent(Constants.KEYCODE_S,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_T:
				return new KeyboardEvent(Constants.KEYCODE_T,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_U:
				return new KeyboardEvent(Constants.KEYCODE_U,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_V:
				return new KeyboardEvent(Constants.KEYCODE_V,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_W:
				return new KeyboardEvent(Constants.KEYCODE_W,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_X:
				return new KeyboardEvent(Constants.KEYCODE_X,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_Y:
				return new KeyboardEvent(Constants.KEYCODE_Y,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_Z:
				return new KeyboardEvent(Constants.KEYCODE_Z,
						Keyboard.getEventKeyState());

			case Keyboard.KEY_ESCAPE:
				return new KeyboardEvent(Constants.KEYCODE_ESC,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_LSHIFT:
			case Keyboard.KEY_RSHIFT:
				return new KeyboardEvent(Constants.KEYCODE_SHIFT,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_LCONTROL:
			case Keyboard.KEY_RCONTROL:
				return new KeyboardEvent(Constants.KEYCODE_CTRL,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_LMETA:
			case Keyboard.KEY_RMETA:
				return new KeyboardEvent(Constants.KEYCODE_ALT,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_SPACE:
				return new KeyboardEvent(Constants.KEYCODE_SPACE,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_TAB:
				return new KeyboardEvent(Constants.KEYCODE_TAB,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_COMMA:
				return new KeyboardEvent(Constants.KEYCODE_COMMA,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_PERIOD:
				return new KeyboardEvent(Constants.KEYCODE_DOT,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_MINUS:
				return new KeyboardEvent(Constants.KEYCODE_MINUS,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_RETURN:
				return new KeyboardEvent(Constants.KEYCODE_RETURN,
						Keyboard.getEventKeyState());

			case Keyboard.KEY_UP:
				scrollY(Keyboard.getEventKeyState() ? 1 : 0);
				return new KeyboardEvent(Constants.KEYCODE_UP,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_DOWN:
				scrollY(Keyboard.getEventKeyState() ? -1 : 0);
				return new KeyboardEvent(Constants.KEYCODE_DOWN,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_LEFT:
				scrollX(Keyboard.getEventKeyState() ? -1 : 0);
				return new KeyboardEvent(Constants.KEYCODE_LEFT,
						Keyboard.getEventKeyState());
			case Keyboard.KEY_RIGHT:
				scrollX(Keyboard.getEventKeyState() ? 1 : 0);
				return new KeyboardEvent(Constants.KEYCODE_RIGHT,
						Keyboard.getEventKeyState());
			
			case Keyboard.KEY_F10:
				return new KeyboardEvent(Constants.KEYCODE_F10,
						Keyboard.getEventKeyState());

			default:
				return new KeyboardEvent(-1, false);
			}
		}

		return null;
	}

	/**
	 * Returns the information of the current mouse state.
	 * 
	 * @return the information of the current mouse state.
	 */
	public MouseInfo getMouseInfo() {
		Mouse.poll();
		int x = Mouse.getX();
		int y = Mouse.getY();

		boolean left = Mouse.isButtonDown(0);
		boolean middle = false;
		boolean right = false;

		if (Mouse.getButtonCount() > 1) {
			middle = Mouse.isButtonDown(1);
			if (Mouse.getButtonCount() > 2) {
				middle = Mouse.isButtonDown(2);
			}
		}

		return new MouseInfo(x, y, left, middle, right);
	}

	/**
	 * Scrolls on the x axis.
	 * 
	 * @param x
	 */
	public void scrollX(int x) {
		if (currentRenderer != null) {
			currentRenderer.scrollX(x);
		}
	}

	/**
	 * Scrolls on the y axis.
	 * 
	 * @param y
	 */
	public void scrollY(int y) {
		if (currentRenderer != null) {
			currentRenderer.scrollY(y);
		}
	}

	/**
	 * Returns the game position of the pixel position.
	 * 
	 * @param x
	 *            The x value of the pixel position.
	 * @param y
	 *            The y value of the pixel position.
	 * 
	 * @return The game position of the pixel position.
	 */
	public Position getGamePosition(int x, int y) {
		if (currentRenderer == null) {
			return null;
		}
		return currentRenderer.getGamePosition(x, y);
	}
	
	/**
	 * Sets the entries which will be shown in the menu.
	 * 
	 * @param entries The entries to show.
	 */
	public void setMenuEntries(String[] entries) {
		if(menuRenderer != null) {
			menuRenderer.setMenuElements(entries);
		}
	}
	
	/**
	 * Sets the selected index of the menu.
	 * 
	 * @param index The index to select.
	 */
	public void setSelectedMenuEntry(int index) {
		if(menuRenderer != null) {
			menuRenderer.setSelected(index);
		}
	}

}
