package de.stealmycode.beehive.model.world;

public class FieldProperty {
	public String name = "";
	public float probability = 1.0f;
	
	@Override
	protected FieldProperty clone() {
		try {
			return (FieldProperty) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}
