package net.minecraft2.world.chunk.storage;

import java.io.File;

import net.minecraft2.util.DataFixer;
import net.minecraft2.world.storage.SaveFormatOld;

public class AnvilSaveConverter extends SaveFormatOld{

	public AnvilSaveConverter(File anvilFile, DataFixer datafixer) {
		super(anvilFile, datafixer);
	}

}
