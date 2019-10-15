package net.minecraft2.network;

import java.net.SocketAddress;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;

import io.netty.channel.ChannelFuture;
import net.minecraft2.server.MinecraftServer;

public class NetworkSystem {

	public volatile boolean isAlive;
	private final MinecraftServer mcServer;
	private static final Logger LOGGER = LogManager.getLogger();
	private final List<ChannelFuture> endpoints = Collections.<ChannelFuture>synchronizedList((List)Lists.newArrayList());
	
	public NetworkSystem(MinecraftServer minecraftServer) {
		this.mcServer = minecraftServer;
		this.isAlive = true;
	}
	public void terminatesEndPoints() {
		this.isAlive = false;
		for(ChannelFuture channelFuture : this.endpoints) {
			try {
				channelFuture.channel().close().sync();
				
			}catch (InterruptedException ex) {
				LOGGER.error("Interruption durant la fermeture de la chaine");
			}
		}
	}

}
