package net.minecraft2.client.resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import net.minecraft2.client.util.JsonUtils;

public class ResourceIndex {

	private final Map<String,File> resourceMap = Maps.<String,File>newHashMap();
	private static final Logger LOGGER = LogManager.getLogger();
	
	BufferedReader bufferedreader = null;
	
	public ResourceIndex() {
		
	}
	
	public ResourceIndex(File assetsDir, String assetIndex) {

		File file1 = new File(assetsDir, "objects");
		File file2 = new File(assetsDir, "indexes/"+assetIndex+".json");
		
		try {
			
			bufferedreader = Files.newReader(file2, StandardCharsets.UTF_8);
			JsonObject jsonobject = (new JsonParser()).parse(bufferedreader).getAsJsonObject();
			JsonObject jsonobject1 = JsonUtils.getJsonObject(jsonobject,"objects",(JsonObject)null);
			
			if(jsonobject1 != null) {
				for(Entry<String, JsonElement> entry : jsonobject1.entrySet()) {
					JsonObject jsonobject2 = (JsonObject)entry.getValue();
					String s = entry.getKey();
					String[] astring = s.split("/",2);
					String s1 = astring.length == 1 ? astring[0] : astring[0] + ":" + astring[1];
					String s2 = JsonUtils.getString(jsonobject2,"hash");
					File file3 = new File(file1,s2.substring(0,2) + "/" + s2);
					this.resourceMap.put(s1, file3);
				}
			}
			
		} catch (FileNotFoundException ex) {
			LOGGER.error("Impossible de trouver l'index du fichier ressource: {}",(Object)file2);
		}catch(JsonParseException ex) {
			LOGGER.error("Impossible de convertir l'index du fichier ressource: {}",(Object)file2);
		}
		
		finally {
			IOUtils.closeQuietly((Reader)bufferedreader);
		}
		
	}

}
