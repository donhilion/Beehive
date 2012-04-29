package de.stealmycode.beehive.config;

import de.stealmycode.beehive.utils.Constants;
import java.util.List;
import java.util.Map;

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
}
