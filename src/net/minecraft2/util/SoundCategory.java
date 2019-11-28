package net.minecraft2.util;

import java.util.Map;

import com.google.common.collect.Maps;

public enum SoundCategory {
	MASTER("master"),
	MUSIC("music"),
	RECORDS("record"),
	WEATHER("weather"),
	BLOCKS("blocks"),
	HOSTILE("hostile"),
	NEUTRAL("neutral"),
	PLAYERS("player"),
	AMBIENT("ambient"),
	VOICE("voice");
	
	private static final Map<String, SoundCategory> SOUND_CATEGORY = Maps.<String, SoundCategory>newHashMap();
	private final String name;
	
	private SoundCategory(String name) {
		this.name = name;
	}
}
