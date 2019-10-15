package net.minecraft2.client;

import java.io.File;
import java.net.Proxy;

import org.lwjgl.Sys;

import net.minecraft2.client.main.GameConfiguration;
import net.minecraft2.server.integrated.IntegratedServer;
import net.minecraft2.util.DataFixer;
import net.minecraft2.util.datafix.DataFixesManager;

public class Minecraft {
	
	
	private static Minecraft instance;
	private IntegratedServer theIntegratedServer;
	public final File mcDataDir;
	
	private final Proxy proxy;
	private final DataFixer dataFixer;
	
	public Minecraft(GameConfiguration gc) {
		proxy = gc.userInfo.proxy == null ? Proxy.NO_PROXY : gc.userInfo.proxy;
		mcDataDir = gc.folderInfo.mcDataDir;
		dataFixer = DataFixesManager.createFixer();
	}
	
	public IntegratedServer getIntegratedServer() {
		return this.theIntegratedServer;
	}
	
	public static void stopIntegratedServer() {
		if(instance != null) {
			IntegratedServer integratedserver = instance.getIntegratedServer();
			if(integratedserver != null) {
				integratedserver.stopServer();
			}
		}
	}
	
	public static long getSystemTime() {
		return Sys.getTime() * 1000L / Sys.getTimerResolution();
	}

	public Proxy getProxy() {
		return this.proxy;
	}

	public DataFixer getDataFixer() {
		return dataFixer;
	}
}
