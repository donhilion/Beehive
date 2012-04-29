package de.stealmycode.beehive.config;

import de.stealmycode.beehive.utils.Constants;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GraphicsConfig extends AbstractConfig {

    @SuppressWarnings("unchecked")
    public Map<Integer, Object> getImages() {
        Map<String, Object> data = (Map<String, Object>) getData();
        return (Map<Integer, Object>) data.get(Constants.GRAPHICS_IMAGES);
    }

    @SuppressWarnings("unchecked")
    public List<Object> getImage(int i) {
        return (List<Object>) getImages().get(i);
    }

    @SuppressWarnings("unchecked")
    public Map<Integer, String> getTextures() {
        Map<String, Object> data = (Map<String, Object>) getData();
        return (Map<Integer, String>) data.get(Constants.GRAPHICS_TEXTURES);
    }

    public String getTexture(int i) {
        return getTextures().get(i);
    }

    public List<Integer> getSpriteIDs() {
        Map<Integer, Object> dataImages = getImages();
        Set<Integer> spriteSet = dataImages.keySet();
        List<Integer> spriteIDs = new LinkedList<Integer>();

        Object[] spriteSetArr = spriteSet.toArray();
        
        for(int i = 0; i < spriteSet.size(); i++) {
            spriteIDs.add(new Integer((Integer) 
                    spriteSet.toArray()[i]));
        }
        
        return spriteIDs;
    }
}
