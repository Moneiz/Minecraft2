package net.minecraft2.client.util;

import java.io.IOException;
import java.io.StringReader;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

public class JsonUtils {

	private static <T> T gsonDeserialize(Gson gsonIn, String json, Class<T> adapter, boolean lenient) {
		return (T)gsonDeserialize(gsonIn, new StringReader(json), adapter, lenient);
	}
	private static <T> T gsonDeserialize(Gson gsonIn, StringReader readerIn, Class<T> adapter, boolean lenient) {

		try {
			JsonReader jsonreader = new JsonReader(readerIn);
			jsonreader.setLenient(lenient);
			return (T)gsonIn.getAdapter(adapter).read(jsonreader);
		}catch(IOException ioex) {
			throw new JsonParseException(ioex);
		}
	}
	public static <T> T gsonDeserialize(Gson gsonIn, String json, Class<T> adapter) {
		return (T)gsonDeserialize(gsonIn, json, adapter, false);
	}
	public static JsonObject getJsonObject(JsonObject jsonobject, String memberName, JsonObject fallback) {
		return jsonobject.has(memberName) ? getJsonObject(jsonobject.get(memberName), memberName) : fallback;
	}
	public static JsonObject getJsonObject(JsonElement jsonElement, String memberName) {
		if(jsonElement.isJsonObject()) {
			return jsonElement.getAsJsonObject();
		}else {
			throw new JsonSyntaxException(memberName+" doit être de type JsonObject");
		}
	}
	public static String getString(JsonObject jsonobject, String string) {
		if(jsonobject.has(string)) {
			return getString(jsonobject.get(string), string);
		}else {
			throw new JsonSyntaxException(string+" est manquant, un String est attendu");
		}
	}
	public static String getString(JsonElement json, String member) {
		if(json.isJsonPrimitive()) {
			return json.getAsString();
		}else {
			throw new JsonSyntaxException(member+" doit être un String");
		}
	}

	
}
