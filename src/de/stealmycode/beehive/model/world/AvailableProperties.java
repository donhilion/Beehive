package de.stealmycode.beehive.model.world;

import java.util.ArrayList;
import java.util.List;

public class AvailableProperties {
	
	private static List<FieldProperty> properties = new ArrayList<FieldProperty>();

	private AvailableProperties() { }
	
	/**
	 * 
	 * @param name Name to identify the property
	 * @param property The Field Properties
	 * @throws IllegalArgumentException if a property using the same name was added earlier
	 * property was replaced
	 */
	public static void addProperty(FieldProperty property) {
		if (getProperty(property.name) != null) {
			throw new IllegalArgumentException("A property using the same name exists.");
		}
		properties.add(property);
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public static FieldProperty getProperty(String name) {
		for (FieldProperty property : properties) {
			if (property.name.equals(name)) {
				return property;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public static List<FieldProperty> getProperties() {
		return properties;
	}
}