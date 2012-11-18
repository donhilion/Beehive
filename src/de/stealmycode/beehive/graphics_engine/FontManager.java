package de.stealmycode.beehive.graphics_engine;

import java.util.HashMap;
import org.newdawn.slick.Color;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import de.stealmycode.beehive.utils.Log;

public class FontManager {
	String fancyPath = "ressources/MysteryQuest-Regular.ttf";
	String uncomplicatedPath = "ressources/Share-Regular.ttf";
	private HashMap<Integer, UnicodeFont> fontOfSize = new HashMap<>();

	/**
	 * Creates Font and saves into HashMap fontOfSize
	 * 
	 * @param size
	 * @return
	 */
	public UnicodeFont createFancyFont(int size) {
		try {
			UnicodeFont font = fontOfSize.get(size);
			if (font == null) {
				font = new UnicodeFont(fancyPath, size, false, false);
				font.addAsciiGlyphs();
				font.addGlyphs(400, 600);
				font.getEffects().add(new ColorEffect(java.awt.Color.yellow));
				font.loadGlyphs();
				fontOfSize.put(size, font);
			}
			return font;
		} catch (Exception e) {
			Log.error("Could not load fancy Font");
			return null;
		}
	}

	/**
	 * Create Simple Font, not used yet
	 * @param uncomplicatedPath
	 * @return
	 */
	public boolean createUncomplicatedFont(String uncomplicatedPath) {
		try {
			UnicodeFont uncomplicated = new UnicodeFont(uncomplicatedPath, 20,
					false, false); // Create Instance
			uncomplicated.addAsciiGlyphs();
			uncomplicated.addGlyphs(400, 600);
			uncomplicated.loadGlyphs();
			return true;
		} catch (Exception e) {
			Log.error("Could not load uncomplicated Font");
			return false;
		}
	}

	/**
	 * Draw's String to x,y using given Font-size, Font needs to be flipped Horizontaly
	 * cause of stupid implementation of UnicodeFont...
	 * @param toDraw
	 * @param x
	 * @param y
	 * @param size
	 * @param highlighted
	 */
	public void drawString(String toDraw, int x, int y, int size,
			boolean highlighted) {
		UnicodeFont font = createFancyFont(size);
		font.addGlyphs(toDraw);
		if (highlighted) {
			font.drawString((float) x, (float) y, toDraw, Color.red);
		} else {
			font.drawString((float) x, (float) y, toDraw, Color.yellow);
		}
	}
}
