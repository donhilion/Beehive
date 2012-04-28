package de.stealmycode.beehive.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.yaml.snakeyaml.Yaml;

import de.stealmycode.beehive.utils.Logger;

public class Config {
	private Object data;
	
	public boolean load(String file) {
		Object data = null;
		try {
			Yaml yaml = new Yaml();
			data = yaml.load(new FileInputStream(file));
			
		} catch (FileNotFoundException e) {
			Logger.loge("Not able to acces file " + file, e, this.getClass());
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
			Logger.loge("Not able to write file " + file, e, this.getClass());
			return false;
		}
		return true;
	}
	
	protected Object getData() {
	    return data;
    }
}
