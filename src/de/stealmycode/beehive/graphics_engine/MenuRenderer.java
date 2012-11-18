package de.stealmycode.beehive.graphics_engine;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import de.stealmycode.beehive.utils.Position;
import de.stealmycode.beehive.graphics_engine.FontManager;

/**
 * 
 * @author maltos
 * 
 */
public class MenuRenderer implements IRenderer {

	private int width;
	private int height;
	private int selected = -1;
	private String[] menuElements;

	private ImageManager imageManager;

	/**
	 * Initiate MenuRenderer
	 * 
	 * @param width
	 * @param height
	 * @return
	 */
	public boolean init(int width, int height) {
		this.width = width;
		this.height = height;

		setViewport();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		return true;
	}

	/**
	 * Draw's Sprites as GL_QUADS using x1,y1,x2,y2
	 * 
	 * @param sprite
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @throws SlickException
	 */
	private void drawElement(Sprite sprite, float x1, float y1, float x2, float y2)
			throws SlickException {
		Color.white.bind();
		sprite.texture.bind();

		GL11.glPushMatrix();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(sprite.x, sprite.y);
		GL11.glVertex2f(x1, height - y1); // oben-links
		GL11.glTexCoord2f(sprite.x + sprite.rectWidth, sprite.y);
		GL11.glVertex2f(x2, height - y1); // oben-rechts
		GL11.glTexCoord2f(sprite.x + sprite.rectWidth, sprite.y
				+ sprite.rectHeight);
		GL11.glVertex2f(x2, height - y2); // unten-rechts
		GL11.glTexCoord2f(sprite.x, sprite.y + sprite.rectHeight);
		GL11.glVertex2f(x1, height - y2); // unten-links
		GL11.glEnd();
		GL11.glPopMatrix();
	}

	/**
	 * Define ImageManager
	 * 
	 * @param imageManager
	 */
	public void setImageRenderer(ImageManager imageManager) {
		this.imageManager = imageManager;
	}

	/**
	 * Set Selected Menu-Entry for highlighting
	 * 
	 * @param index
	 */
	public void setSelected(int index) {
		this.selected = index;
	}

	/**
	 * Defines Menu-Entrys
	 * 
	 * @param elements
	 */
	public void setMenuElements(String[] elements) {
		this.menuElements = elements;
	}

	/**
	 * Draw's the Menu including 2 pictures for ambiente..
	 */
	public void draw() {
		try {
			if (imageManager == null) return;
			setViewport();
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

			Sprite spriteToDraw;
			
			spriteToDraw = imageManager.getSprite(39); // königin bild
			drawElement(spriteToDraw, 10, 10, 310, 310);
			
			spriteToDraw = imageManager.getSprite(38); // worrior bild
			drawElement(spriteToDraw, 500, 280, 800, 580);

			
			// GL11 needs to flip String horizontaly
			FontManager fontMan = new FontManager();
			GL11.glPushMatrix();
			GL11.glScalef(1.0f, -1.0f, 1.0f);
			for (int n = 4; n >= 0; n--) {
				if (selected == n) {
					fontMan.drawString(menuElements[n], (width / 2) - 160, -1
							* height + 30 + n * 100, 60, true);
				} else {
					fontMan.drawString(menuElements[n], (width / 2) - 160, -1
							* height + 30 + n * 100, 60, false);
				}
			}
			GL11.glPopMatrix();

		} catch (SlickException e) {
		}
	}

	@Override
	public void scrollX(int x) {
		return;
	}

	@Override
	public void scrollY(int y) {
		return;
	}

	@Override
	public Position getGamePosition(int x, int y) {
		return new Position(x, y);
	}

	private void setViewport() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, 0, height, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}
}