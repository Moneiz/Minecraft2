package net.minecraft2.client.resources;

import java.io.File;

public class ResourceIndexFolder extends ResourceIndex {

	private final File baseDir;
	
	public ResourceIndexFolder(File assetsDir) {
		this.baseDir = assetsDir;
	}

}
