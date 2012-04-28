package de.stealmycode.beehive.model.world;

import java.util.HashMap;
import java.util.Map;

public class AvailableProperties {
	
	private static Map<String, FieldProperty> properties = new HashMap<String, FieldProperty>();

	private AvailableProperties() { }
	
	/**
	 * 
	 * @param name Name to identify the property
	 * @param property The Field Properties
	 * @throws IllegalArgumentException if a property using the same name was added earlier
	 * property was replaced
	 */
	public void addProperty(String name, FieldProperty property) {
		if (properties.put(name, property) != null) {
			throw new IllegalArgumentException();
		}
	}
}