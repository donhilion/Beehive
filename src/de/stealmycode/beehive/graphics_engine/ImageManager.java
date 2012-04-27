package de.stealmycode.beehive.graphics_engine;

import java.util.Date;

import org.newdawn.slick.opengl.Texture;

/**
 * This class loads the images containing the sprites
 * and delivers them.
 * 
 * @author donhilion
 *
 */
public class ImageManager {
	
	/**
	 * Contains the mapping of imageIds to spriteIds
	 * 
	 * imageIdToSpriteId[i] = [spriteId0, spriteId1, ..., spriteIdn]
	 * imageIdToSpriteId[i][tick%n] = spriteToDraw
	 */
	private int[][] imageIdToSpriteId;
	
	/**
	 * The texture containing the image.
	 */
	private Texture texture;
	
	/**
	 * Determines how many milliseconds have to pass till the next
	 * sprite is shown.
	 */
	private static final int renderRate = 100;
	
	/**
	 * Loads the given file in an image.
	 * This image contains all sprites to render.
	 * 
	 * @param fileName The filename of the image.
	 * 
	 * @return true if the image was successfully loaded.
	 */
	public boolean loadImage(String fileName) {
		// TODO implement
		return false;
	}
	
	/**
	 * Loads the given file of the image description.
	 * This description contains informations of the rectangles
	 * which contains a sprite.
	 * Also information about the imageId and the associated sprites
	 * is given in this description.
	 * 
	 * @param fileName The filename of the description.
	 * 
	 * @return true if the image was successfully loaded.
	 */
	public boolean loadImageDescription(String fileName) {
		// TODO define file format
		// TODO implement
		return false;
	}
	
	/**
	 * Returns the sprite of the given imageId which is
	 * associated with the current tick.
	 * 
	 * @param imageId The id of the image to render.
	 * 
	 * @return The sprite of the imageId.
	 */
	public Sprite getSprite(int imageId) {
		// TODO implement
		if(imageId < 0 || imageId >= imageIdToSpriteId.length) {
			// TODO error handling
			return null;
		}
		int n = imageIdToSpriteId[imageId].length;
		int spriteId = imageIdToSpriteId[imageId][(int)((new Date().getTime()/renderRate)%n)];
		return null;
	}

}
