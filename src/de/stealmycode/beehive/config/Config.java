/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.stealmycode.beehive.config;

import de.stealmycode.beehive.utils.Constants;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 *
 * @author fate
 */
public class Config extends AbstractConfig {
    public Map<String, Float> getFieldProperties(String difficulty) {
        Map<String, Object> data = (Map<String, Object>) getData();
        Map<String, Object> data_difficulties = (Map<String, Object>) data.get(difficulty);
        return (Map<String, Float>) data_difficulties.get(Constants.CONFIG_FIELD_PROPERTIES);
    }
    
    public Float getBaseProbability() {
        Map<String, Object> data = (Map<String, Object>) getData();
        Map<String, Object> data_global = (Map<String, Object>) data.get(Constants.CONFIG_GLOBAL);
        return (Float) data_global.get(Constants.CONFIG_BASE_PROBABILITY);
    }

    public List<Integer> getBlacklistFor(String name) {
        Map<String, Object> data = (Map<String, Object>) getData();
        Map<String, Object> data_blacklist = (Map<String, Object>) data.get(Constants.CONFIG_BLACKLIST);
        return (List<Integer>) data_blacklist.get(name);
    }
    
    public Level getGlobalLogLevel() {
        Map<String, Object> data = (Map<String, Object>) getData();
        Map<String, Object> data_global = (Map<String, Object>) data.get(Constants.CONFIG_GLOBAL);
        return Level.parse((String) data_global.get(Constants.CONFIG_LOGLEVEL));     
    } 
}
