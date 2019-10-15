package net.minecraft2.world.storage;

import java.io.File;

import net.minecraft2.util.DataFixer;

public class SaveFormatOld implements ISaveFormat{

	protected final File savesDirectory;
	protected final DataFixer dataFixer;
	
	public SaveFormatOld(File anvilFile, DataFixer datafixer) {
		this.dataFixer = datafixer;
		if(!anvilFile.exists()) {
			anvilFile.mkdirs();
		}
		this.savesDirectory = anvilFile;
	}

}
