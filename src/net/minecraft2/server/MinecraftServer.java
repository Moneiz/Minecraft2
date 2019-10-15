package net.minecraft2.server;

import java.io.File;
import java.net.Proxy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;

import net.minecraft2.command.ICommandManager;
import net.minecraft2.command.ServerCommandManager;
import net.minecraft2.network.NetworkSystem;
import net.minecraft2.profiler.ISnooperInfo;
import net.minecraft2.profiler.Snooper;
import net.minecraft2.server.management.PlayerList;
import net.minecraft2.server.management.PlayerProfileCache;
import net.minecraft2.util.DataFixer;
import net.minecraft2.world.WorldServer;
import net.minecraft2.world.chunk.storage.AnvilSaveConverter;
import net.minecraft2.world.storage.ISaveFormat;

public abstract class MinecraftServer implements Runnable , ISnooperInfo{

	private static final Logger LOG = LogManager.getLogger();
	private final YggdrasilAuthenticationService authService;
	private final NetworkSystem networkSystem;
	private final PlayerProfileCache profileCache;
	private final GameProfileRepository profileRepo;
	protected final Proxy serverProxy;
	private final MinecraftSessionService sessionService;
	private final File anvilFile;
	private final DataFixer datafixer;
	public final ICommandManager commandManager;
	private final ISaveFormat anvilConverterForAnvilFile;
	private PlayerList playerList;
	public WorldServer[] worldServers;
	private final Snooper usageSnooper = new Snooper("server", this, getCurrentTimeMillis());
	
	public MinecraftServer(File anvilFile, Proxy proxy,DataFixer datafixer, 
			YggdrasilAuthenticationService authService,MinecraftSessionService sessionService,
			GameProfileRepository profileRepo, PlayerProfileCache profileCache) {
		this.serverProxy = proxy;
		this.authService = authService;
		this.sessionService = sessionService;
		this.profileRepo = profileRepo;
		this.profileCache = profileCache;
		this.anvilFile = anvilFile;
		this.networkSystem = new NetworkSystem(this);
		this.commandManager = this.createNewCommandManager();
		this.anvilConverterForAnvilFile = new AnvilSaveConverter(anvilFile, datafixer);
		this.datafixer = datafixer;
	}
	
	public static long getCurrentTimeMillis() {
		return System.currentTimeMillis();
	}

	public ServerCommandManager createNewCommandManager() {
		return new ServerCommandManager(this);
	}

	private NetworkSystem getNetworkSystem() {
		return networkSystem;
	}

	public void stopServer() {
		LOG.info("Arret du serveur");
		if(this.getNetworkSystem() != null) {
			this.getNetworkSystem().terminatesEndPoints();
		}
		if(this.playerList != null) {
			LOG.info("Sauvegarde des joueurs");
			this.playerList.saveAllPlayerData();
			this.playerList.removeAllPlayers();
		}
		if(this.worldServers != null) {
			LOG.info("Sauvegarde des mondes");
			for(WorldServer worldServer : worldServers) {
				if(worldServer != null) {
					worldServer.disableLevelSaving = false;
				}
			}
			this.saveAllWorlds(false);
			for(WorldServer worldServer1 : this.worldServers) {
				if(worldServer1 != null) {
					worldServer1.flush();
				}
			}
		}
		if(this.usageSnooper.isSnooperRunning()) {
			this.usageSnooper.stopSnooper();
		}
	}

	private void saveAllWorlds(boolean b) {
		
	}

}
