package net.minecraft2.world;

import net.minecraft2.world.storage.ISaveHandler;

public class World {

	protected final ISaveHandler saveHandler;
	public World(ISaveHandler saveHandler) {
		this.saveHandler = saveHandler;
	}
}
