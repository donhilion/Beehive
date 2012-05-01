package de.stealmycode.beehive.model.world;

import java.util.ArrayList;
import java.util.List;

import de.stealmycode.beehive.utils.Position;

public class Field {
	private Position					position;
	private List<FieldProperty>	properties = new ArrayList<FieldProperty>();
	
	/**
	 * 
	 * @param position Position of the field
	 * @param property Initial Property added to FieldProperty
	 */
	public Field(Position position) {
		this.position = position;
	}
	
	/**
	 * 
	 * @return 
	 */
	public Position getPosition() {
		return position;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getProbability() {
		double result = 1.0;
		for (FieldProperty property: properties) {
			result *= property.getProbability();
		}
		return result;
	}
	
	/**
	 * 
	 * @param property
	 */
	public void addProperty(FieldProperty property) {
		properties.add(property);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<FieldProperty> getProperties() {
		return properties;
	}

    @Override
    public String toString() {
        String str = "";
        for(FieldProperty property : this.properties) 
            str += "," + property;
        return str;
    }
}
