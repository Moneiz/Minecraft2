package net.minecraft2.client.settings;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import net.minecraft2.client.resources.I18n;
import net.minecraft2.util.IntHashMap;

public class KeyBinding implements Comparable<KeyBinding> {

	private static final Map<String, KeyBinding> KEYBIND_ARRAY = Maps.<String, KeyBinding>newHashMap();
	private static final IntHashMap<KeyBinding> HASH = new IntHashMap<KeyBinding>();
	private static final Set<String> KEYBIND_SET = Sets.<String>newHashSet();
	private static final Map<String, Integer> KEYBIND_CATEGORY = Maps.<String, Integer>newHashMap();
	private final String keyDescription;
	private final int keyCodeDefault;
	private final String keyCategory;
	private int keyCode;
	
	private boolean pressed;
	private int pressTime;
	
	public KeyBinding(String description, int keyCode, String category) {
		
		this.keyDescription = description;
		this.keyCode = keyCode;
		this.keyCodeDefault = keyCode;
		this.keyCategory = category;
		KEYBIND_ARRAY.put(description, this);
		HASH.addKey(keyCode, this);
		KEYBIND_SET.add(category);
		
	}
	public int compareTo(KeyBinding o) {
		if(this.keyCategory.equals(o.keyCategory)) {
			return I18n.format(this.keyDescription).compareTo(I18n.format(o.keyDescription));
		}else {
			return ((Integer) KEYBIND_CATEGORY.get(this.keyCategory)).compareTo(KEYBIND_CATEGORY.get(o.keyCategory));
		}
	}

}
