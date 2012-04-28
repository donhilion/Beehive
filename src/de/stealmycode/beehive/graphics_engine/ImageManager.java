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
	 * rectangleOfSprite[spriteId] = [textureId, x, y, width, height, rectWidth, rectHeight]
	 */
	private float[][] rectangleOfSprite;
	
	/**
	 * The array containing the textures containing the images.
	 */
	private Texture[] textures;
	
	/**
	 * Determines how many milliseconds have to pass till the next
	 * sprite is shown.
	 */
	private static final int renderRate = 500;
	
	/**
	 * Loads the files described by the given description file.
	 * 
	 * @param fileName The filename of the image.
	 * 
	 * @return true if the image was successfully loaded.
	 */
	public boolean loadImage(String path, String fileName) {
		try {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(ResourceLoader.getResourceAsStream(
							path + "/" + fileName)));
			
			HashMap<Integer, Texture> idToTexture = new HashMap<Integer, Texture>();
			int maxId = 0;
			
			String line = reader.readLine();
			while(line != null) {
				try {
					String[] parts = line.split(", ");
					int id = Integer.parseInt(parts[0]);
					Texture texture = 
							TextureLoader.getTexture("PNG", 
									ResourceLoader.getResourceAsStream(
											path + "/" + parts[1]));
					idToTexture.put(id, texture);
					if(id > maxId) {
						maxId = id;
					}
				} catch(Exception e) {
					Logger.logw("Could not parse texture description line: " + line, this.getClass());
				} finally {
					line = reader.readLine();
				}
			}
			textures = new Texture[maxId+1];
			for(Integer key : idToTexture.keySet()) {
				textures[key] = idToTexture.get(key);
			}
			
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
	 * imageId, textureId, x, y, width, height, optional comment
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
				if(parts.length >= 6) {
					try {
						Integer[] values = new Integer[6];
						for(int i=0; i<6; i++) {
							values[i] = Integer.parseInt(parts[i]);
						}
						readedList.add(values);
					} catch(Exception e) {
						Logger.logw("Could not parse description line: " + line, this.getClass());
					} finally {
						line = reader.readLine();
					}
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
					float[] rectangle = new float[7];
					
					if(array[1] < 0 || array[1] >= textures.length) {
						continue;
					}
					
					// size of rectangle must be calculated to the texture size
					rectangle[0] = array[1];
					Texture texture = textures[array[1]];
					rectangle[1] = ((float) array[2])/((float) texture.getImageWidth())*texture.getWidth(); // x
					rectangle[2] = ((float) array[3])/((float) texture.getImageHeight())*texture.getHeight(); // y
					rectangle[3] = array[4]; // width
					rectangle[4] = array[5]; // height
					rectangle[5] = ((float) array[4])/((float) texture.getImageWidth())*texture.getWidth(); // rectWidth
					rectangle[6] = ((float) array[5])/((float) texture.getImageHeight())*texture.getHeight(); // rectHeight
					
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
		sprite.texture = textures[(int)rectangle[0]];
		sprite.x = rectangle[1];
		sprite.y = rectangle[2];
		sprite.width = rectangle[3];
		sprite.height = rectangle[4];
		sprite.rectWidth = rectangle[5];
		sprite.rectHeight = rectangle[6];
		
		return sprite;
	}

}
