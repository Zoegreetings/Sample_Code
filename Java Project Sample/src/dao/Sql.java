package edu.gatech.saad.p3.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Sql {

	private static boolean loaded = false;

	public static Map<String, String> SqlData = new HashMap<String, String>();
	static{
		readXML();
	}

	public static void main(String[] args) {
		readXML();
	}

	public static void readXML() {
		
		System.out.println(System.getProperty("user.dir"));
		
		if (loaded)
			return;
		try {
			File file = new File("src/edu/gatech/saad/p3/dao/sql.xml");
			FileInputStream fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.loadFromXML(fileInput);
			fileInput.close();

			Enumeration<?> enuKeys = properties.keys();
			while (enuKeys.hasMoreElements()) {
				String key = (String) enuKeys.nextElement();
				String value = properties.getProperty(key);
				System.out.println(key + ": " + value);
				SqlData.put(key, value);
			}

			loaded = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
