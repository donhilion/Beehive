package de.stealmycode.beehive.model.world;

import java.util.ArrayList;
import java.util.List;

public class AvailableProperties {
	
	private List<FieldProperty> properties = new ArrayList<FieldProperty>();

        /**
         * 
         */
        public AvailableProperties() { }
        
        /**
         * 
         * @param properties 
         */
	public AvailableProperties(List<FieldProperty> properties) {
            this.properties = properties;
        }        
        
        /**
         * 
         */
        public void clearProperties() {
            properties.clear();
        }
        
	/**
	 * 
	 * @param name Name to identify the property
	 * @param property The Field Properties
	 * @throws IllegalArgumentException if a property using the same name was added earlier
	 * property was replaced
	 */
	public void addProperty(FieldProperty property) {
		if (getProperty(property.getName()) != null) {
			throw new IllegalArgumentException("A property using the same name exists.");
		}
		properties.add(property);
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public FieldProperty getProperty(String name) {
		for (FieldProperty property : properties) {
			if (property.getName().equals(name)) {
				return property;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<FieldProperty> getProperties() {
		return properties;
	}
}