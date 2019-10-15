package net.minecraft2.world;

import net.minecraft2.world.storage.ISaveHandler;

public class WorldServer extends World {

	public WorldServer(ISaveHandler saveHandler) {
		super(saveHandler);
	}

	public boolean disableLevelSaving;
	
	public void flush() {
		this.saveHandler.flush();
	}

}
