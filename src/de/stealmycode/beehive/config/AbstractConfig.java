package de.stealmycode.beehive.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.yaml.snakeyaml.Yaml;

import de.stealmycode.beehive.utils.Log;

public abstract class AbstractConfig {
	private Object data;
	
	public boolean load(String file) {
		Object data = null;
		try {
			Yaml yaml = new Yaml();
			data = yaml.load(new FileInputStream(file));
			
		} catch (FileNotFoundException e) {
		    Log.error("Not able to acces file \"" + file+ "\". File not found");
		}
		if (data == null) {
			return false;
		}
		this.data = data;
		return true;
	}
	
	public boolean save(String file) {
		try {
			Yaml yaml = new Yaml();
			FileWriter writer = new FileWriter(new File(file));
			yaml.dump(data, writer);
			writer.close();
		} catch (IOException e) {
		    Log.error("Not able to write file " + file);
			return false;
		}
		return true;
	}
	
	protected Object getData() {
	    return data;
    }
}
