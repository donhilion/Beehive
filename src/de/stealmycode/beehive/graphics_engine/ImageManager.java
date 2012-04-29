package de.stealmycode.beehive.graphics_engine;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import de.stealmycode.beehive.config.GraphicsConfig;


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
	 * Contains the sprites.
	 */
	private Sprite[] sprites;
	
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
	 * Loads the given yaml configuration file and
	 * the textures which are described in it.
	 * Also, the described sprites will be generated.
	 * 
	 * @param fileName
	 * @return true, if the loading was successful
	 */
	public boolean loadConfig(String fileName) {
		GraphicsConfig config = new GraphicsConfig();
		
		if(!config.load(fileName)) {
			return false;
		}
		Map<Integer, String> textureMap = config.getTextures();
		
		// get max id
		int max = 0;
		for(int key : textureMap.keySet()) {
			if(key > max) {
				max = key;
			}
		}
		
		textures = new Texture[max+1];
		
		// load textures
		for(int key : textureMap.keySet()) {
			try {
				textures[key] = 
						TextureLoader.getTexture("PNG", 
								ResourceLoader.getResourceAsStream(
										textureMap.get(key)));
			} catch (IOException e) {
				Logger.getGlobal().log(
						Level.WARNING, "Could not load texture: " + key, e);
			}
		}
		
		Map<Integer, Object> imageMap = config.getImages();
		
		// get max id
		max = 0;
		for(Integer key : imageMap.keySet()) {
			if(key > max) {
				max = key;
			}
		}
		
		int spriteId = 0;
		imageIdToSpriteId = new int[max+1][];
		
		
		LinkedList<Sprite> tempSprites = new LinkedList<Sprite>();
		HashMap<Integer, List<Integer>> tempIds = new HashMap<Integer, List<Integer>>();
		
		for(Integer key : imageMap.keySet()) {
			Object listObject = imageMap.get(key);
			if(listObject instanceof List<?>) {
				List<?> list = (List<?>) listObject;
				List<Integer> ids = new LinkedList<Integer>();
				for(Object o : list) {
					if(o instanceof List<?>) {
						List<?> entries = (List<?>) o;
						try {
							Sprite sprite = new Sprite();
							sprite.texture = textures[(Integer) entries.get(0)];
							sprite.x = ((Integer) entries.get(1))/
									((float) sprite.texture.getImageWidth())*sprite.texture.getWidth();
							sprite.y = ((Integer) entries.get(2))/
									((float) sprite.texture.getImageHeight())*sprite.texture.getHeight();
							sprite.width = ((Number) entries.get(5)).floatValue();
							sprite.height = ((Number) entries.get(6)).floatValue();
							sprite.rectWidth = (Integer) entries.get(3) /
									((float) sprite.texture.getImageWidth())*sprite.texture.getWidth();
							sprite.rectHeight = (Integer) entries.get(4) /
									((float) sprite.texture.getImageHeight())*sprite.texture.getHeight();
							
							tempSprites.add(sprite);
							ids.add(spriteId);
							spriteId++;
						} catch(Exception e) {
							Logger.getGlobal().log(Level.WARNING, "Could not read sprite entry");
						}
					}
				}
				tempIds.put(key, ids);
			}
		}
		
		sprites = new Sprite[tempSprites.size()];
		for(int i=0; i<tempSprites.size(); i++) {
			sprites[i] = tempSprites.get(i);
		}
		
		max = 0;
		for(Integer key : tempIds.keySet()) {
			if(key > max) {
				max = key;
			}
		}
		
		imageIdToSpriteId = new int[max+1][];
		for(Integer key : tempIds.keySet()) {
			List<Integer> list = tempIds.get(key);
			imageIdToSpriteId[key] = new int[list.size()];
			for(int i=0; i<list.size(); i++) {
				imageIdToSpriteId[key][i] = list.get(i);
			}
		}
		
		
		return true;
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
//			Logger.logw("imageId out of range"+imageId, this.getClass());
			// TODO
			return null;
		}
		
		if(imageIdToSpriteId[imageId] == null) {
//			Logger.logw("No sprites for imageId "+imageId, this.getClass());
			// TODO
			return null;
		}
		
		int n = imageIdToSpriteId[imageId].length;
		if(n == 0) {
//			Logger.logw("Empty sprite list for imageId "+imageId, this.getClass());
			// TODO
			return null;
		}
		int spriteId = imageIdToSpriteId[imageId][(int)((new Date().getTime()/renderRate)%n)];
		
		if(spriteId < 0 || spriteId >= sprites.length) {
//			Logger.logw("spriteId out of range: "+spriteId, this.getClass());
			// TODO
			return null;
		}
		
		return sprites[spriteId];
	}

}
