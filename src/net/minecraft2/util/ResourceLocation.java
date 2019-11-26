package net.minecraft2.util;

import java.lang.reflect.Type;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft2.client.util.JsonUtils;

public class ResourceLocation implements Comparable<ResourceLocation>{
	protected final String resourceDomain;
	protected final String resourcePath;
	
	public ResourceLocation(String resourceName) {
		this(0, splitObjectName(resourceName));
	}

	public ResourceLocation(int i, String...resourceName) {
		this.resourceDomain = StringUtils.isEmpty(resourceName[0]) ? "minecraft" : resourceName[0].toLowerCase(Locale.ROOT);
		this.resourcePath = resourceName[1].toLowerCase(Locale.ROOT);
		Validate.notNull(this.resourcePath);
	}

	protected static String[] splitObjectName(String resourceName) {
		String[] result = new String[] {"minecraft", resourceName};
		int i = resourceName.indexOf(58);
		if(i >= 0) {
			result[1] = resourceName.substring(i+1, resourceName.length());
			if(i > 1) {
				result[0] = resourceName.substring(0, i);
			}
		}
		return result;
	}
	public String toString() {
		return this.resourceDomain + ":"+this.resourcePath;
	}
	public boolean equals(Object other) {
		if(this == other) {
			return true;
		}else if(!(other instanceof ResourceLocation)) {
			return false;
		}else {
			ResourceLocation ressource = (ResourceLocation)other;
			return this.resourceDomain.equals(ressource.resourceDomain) && this.resourcePath.equals(ressource.resourcePath);
		}
	}
	public int hashCode() {
		return 31 * this.resourceDomain.hashCode() + this.resourcePath.hashCode();
	}
	public int compareTo(ResourceLocation other) {
		int i = this.resourceDomain.compareTo(other.resourceDomain);
		if(i==0) {
			i = this.resourcePath.compareTo(other.resourcePath);
		}
		return i;
	}
	public static class Serializer implements JsonDeserializer<ResourceLocation>, JsonSerializer<ResourceLocation>{

		@Override
		public JsonElement serialize(ResourceLocation arg0, Type arg1, JsonSerializationContext arg2) {
			return new JsonPrimitive(arg0.toString());
		}

		public ResourceLocation deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2)
				throws JsonParseException {

			return new ResourceLocation(JsonUtils.getString(arg0,"location"));
		}
		
	}
}
