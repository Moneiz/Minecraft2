package net.minecraft2.client.resources;

import java.util.IllegalFormatException;
import java.util.Map;

import com.google.common.collect.Maps;

public class Locale {

	
	Map<String, String> properties = Maps.<String, String>newHashMap();

	public String formatMessage(String translateKey, Object[] parameters){
		String s = this.translateKeyPrivate(translateKey);
		
		try {
			return String.format(s, parameters);
			
		}catch(IllegalFormatException e) {
			return "Format error: " + s;
		}
	}

	private String translateKeyPrivate(String translateKey) {
		String s = this.properties.get(translateKey);
		return s == null ? translateKey : s;
	}
	
}
