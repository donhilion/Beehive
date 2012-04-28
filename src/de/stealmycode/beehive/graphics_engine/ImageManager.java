package de.stealmycode.beehive.graphics_engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import de.stealmycode.beehive.utils.Logger;


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
	 * Contains the rectangles to the sprites.
	 * 
	 * rectangleOfSprite[spriteId] = [x, y, width, height, rectWidth, rectHeight]
	 */
	private float[][] rectangleOfSprite;
	
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
		try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(fileName));
			return true;
		} catch (Exception e) {
			Logger.loge("Could not load image", e, this.getClass());
			return false;
		}
	}
	
	/**
	 * Loads the given file of the image description.
	 * This description contains informations of the rectangles
	 * which contains a sprite.
	 * Also information about the imageId and the associated sprites
	 * is given in this description.
	 * 
	 * File formate:
	 * imageId, x, y, width, height, optional comment
	 * 
	 * @param fileName The filename of the description.
	 * 
	 * @return true if the image was successfully loaded.
	 */
	public boolean loadImageDescription(String fileName) {
		try {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(ResourceLoader.getResourceAsStream(fileName)));
			LinkedList<Integer[]> readedList = new LinkedList<Integer[]>();
			String line = reader.readLine();
			while(line != null) {
				String[] parts = line.replace(" ", "").split(",");
				if(parts.length >= 5) {
					try {
						Integer[] values = new Integer[5];
						for(int i=0; i<5; i++) {
							values[i] = Integer.parseInt(parts[i]);
						}
						readedList.add(values);
					} catch(Exception e) {
						Logger.logw("Could not parse description line: " + line, this.getClass());
					}
					line = reader.readLine();
				}
			}
			// summarize by imageId
			int maxId = 0;
			HashMap<Integer, List<Integer[]>> temporaryMap = new HashMap<Integer, List<Integer[]>>();
			for(Integer[] l : readedList) {
				List<Integer[]> list = temporaryMap.get(l[0]);
				if(list == null) {
					list = new LinkedList<Integer[]>();
					temporaryMap.put(l[0], list);
				}
				list.add(l);
				// get max id for array size
				if(l[0] > maxId) {
					maxId = l[0];
				}
			}
			
			// put lists in an array for faster access.
			int spriteId = 0;
			rectangleOfSprite = new float[readedList.size()][];
			imageIdToSpriteId = new int[maxId+1][];
			
			for(int key : temporaryMap.keySet()) {
				List<Integer[]> list = temporaryMap.get(key);
				int[] spriteIds = new int[list.size()];
				for(int i=0; i<list.size(); i++) {
					Integer[] array = list.get(i);
					float[] rectangle = new float[6];
					
					// size of rectangle must be calculated to the texture size
					rectangle[0] = ((float) array[1])/((float) texture.getImageWidth())*texture.getWidth(); // x
					rectangle[1] = ((float) array[2])/((float) texture.getImageHeight())*texture.getHeight(); // y
					rectangle[2] = array[3]; // width
					rectangle[3] = array[4]; // height
					rectangle[4] = ((float) array[3])/((float) texture.getImageWidth())*texture.getWidth(); // rectWidth
					rectangle[5] = ((float) array[4])/((float) texture.getImageHeight())*texture.getHeight(); // rectHeight
					
					rectangleOfSprite[spriteId] = rectangle;
					spriteIds[i] = spriteId;
					spriteId++;
				}
				imageIdToSpriteId[key] = spriteIds;
			}
			
			return true;
		} catch (IOException e) {
			Logger.loge("Could not load description file.", e, this.getClass());
			return false;
		}
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
		if(imageId < 0 || imageId >= imageIdToSpriteId.length) {
			Logger.logw("imageId out of range"+imageId, this.getClass());
			return null;
		}
		
		if(imageIdToSpriteId[imageId] == null) {
			Logger.logw("No sprites for imageId "+imageId, this.getClass());
			return null;
		}
		
		int n = imageIdToSpriteId[imageId].length;
		if(n == 0) {
			Logger.logw("Empty sprite list for imageId "+imageId, this.getClass());
			return null;
		}
		int spriteId = imageIdToSpriteId[imageId][(int)((new Date().getTime()/renderRate)%n)];
		
		if(spriteId < 0 || spriteId >= rectangleOfSprite.length) {
			Logger.logw("spriteId out of range: "+spriteId, this.getClass());
			return null;
		}
		
		float[] rectangle = rectangleOfSprite[spriteId];
		
		Sprite sprite = new Sprite();
		sprite.texture = texture;
		sprite.x = rectangle[0];
		sprite.y = rectangle[1];
		sprite.width = rectangle[2];
		sprite.height = rectangle[3];
		sprite.rectWidth = rectangle[4];
		sprite.rectHeight = rectangle[5];
		
		return sprite;
	}

}
