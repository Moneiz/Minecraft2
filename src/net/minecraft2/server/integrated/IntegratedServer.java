package net.minecraft2.server.integrated;

import java.io.File;
import java.net.Proxy;

import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;

import net.minecraft2.client.Minecraft;
import net.minecraft2.client.multiplayer.ThreadLanServerPing;
import net.minecraft2.server.MinecraftServer;
import net.minecraft2.server.management.PlayerProfileCache;
import net.minecraft2.util.DataFixer;
import net.minecraft2.world.WorldSetting;

public class IntegratedServer extends MinecraftServer {

	private ThreadLanServerPing lanServerPing;

	public IntegratedServer(Minecraft client, String folderName, String worldName,
			WorldSetting worldSettings, YggdrasilAuthenticationService authService,
			MinecraftSessionService sessionService, GameProfileRepository profileRepo,
			PlayerProfileCache profileCache) {
		super(new File(client.mcDataDir,"saves"),client.getProxy(),client.getDataFixer(), authService,
				sessionService, profileRepo, profileCache);
	}

	@Override
	public void run() {
		
	}

	public void stopServer() {
		super.stopServer();
		
		if(this.lanServerPing != null) {
			this.lanServerPing.interrupt();
			this.lanServerPing = null;
		}
	}

}
