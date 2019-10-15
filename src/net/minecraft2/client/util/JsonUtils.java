package net.minecraft2.client.util;

import java.io.IOException;
import java.io.StringReader;

import com.google.gson.Gson;
import com.google.gson.JsonNull;
import com.google.gson.JsonParseException;
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

	
}
