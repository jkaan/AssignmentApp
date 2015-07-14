package nl.hz.persistency;

import java.io.File;
import java.io.IOException;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;

public class Config {
	private static Ini ini;
	private static String env;
	
	private Config(){
		
	}
	
	public static final String get(String key){
		if(Config.ini == null){
			try {
				File configFile = new File("environment.ini");
				if(!configFile.exists()){
					configFile.createNewFile();
				}
				Config.ini = new Ini(configFile);
				Config.env = ini.get("environment", "environment");
			} catch (InvalidFileFormatException e1) {
				e1.printStackTrace();
				return null;
			} catch (IOException e1) {
				e1.printStackTrace();
				return null;
			}
		}
		
		return Config.ini.get(Config.env, key);
	}
}
