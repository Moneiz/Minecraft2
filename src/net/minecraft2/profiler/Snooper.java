package net.minecraft2.profiler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;

import net.minecraft2.server.MinecraftServer;

public class Snooper implements ISnooperInfo{

	private String side;
	private final URL serverUrl;
	private final long minecraftStartTimeMillis;
	private final ISnooperInfo playerStatsCollector;
	private boolean isRunning;
	private final Timer threadTrigger = new Timer("Snooper Timer",true);
	
	public Snooper(String side, ISnooperInfo playStatsCollector, long currentTimeMillis) {

		try {
			this.serverUrl = new URL("http://snoop.minecraft.net/"+side+"?version="+2);
		}catch(MalformedURLException ex) {
			throw new IllegalArgumentException();
		}
		this.playerStatsCollector = playStatsCollector;
		this.minecraftStartTimeMillis = currentTimeMillis;
		
	}

	public boolean isSnooperRunning() {
		return this.isRunning;
	}

	public void stopSnooper() {
		this.threadTrigger.cancel();
	}

}
