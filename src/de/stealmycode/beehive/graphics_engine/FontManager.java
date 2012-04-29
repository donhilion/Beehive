package de.stealmycode.beehive.graphics_engine;

import java.awt.Color;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import de.stealmycode.beehive.utils.Log;

public class FontManager {
	String fancyPath = "ressources/MysteryQuest-Regular.ttf";
	String uncomplicatedPath = "ressources/Share-Regular.ttf";
	
	public boolean createFancyFont(String fancyPath){
		try {
			UnicodeFont fancy = new UnicodeFont(fancyPath , 20, false, false); //Create Instance
			fancy.addAsciiGlyphs();   //Add Glyphs
			fancy.addGlyphs(400, 600); //Add Glyphs
			fancy.getEffects().add(new ColorEffect(Color.BLACK)); //Add Effects
			fancy.loadGlyphs();  //Load Glyphs
			return true;
		} catch (Exception e) {
				Log.error("Could not load fancy Font");
				return false;
			}
		}
	
	public boolean createUncomplicatedFont(String uncomplicatedPath){
		try {
			UnicodeFont uncomplicated = new UnicodeFont(uncomplicatedPath , 20, false, false); //Create Instance
			uncomplicated.addAsciiGlyphs();   //Add Glyphs
			uncomplicated.addGlyphs(400, 600); //Add Glyphs
			uncomplicated.getEffects().add(new ColorEffect(Color.BLACK)); //Add Effects
			uncomplicated.loadGlyphs();  //Load Glyphs
			return true;
		} catch (Exception e) {
				Log.error("Could not load uncomplicated Font");
				return false;
			}
		}
	/**Usage of the fonts:
	 * public void enterName(String s){
  		fancy.addGlyphs(s);
  		fancy.load();
		}
	 */
}

