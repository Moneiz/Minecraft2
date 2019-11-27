package net.minecraft2.client;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.DisplayMode;

import com.google.common.collect.Lists;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;

import net.minecraft2.client.gui.toasts.GuiToast;
import net.minecraft2.client.main.GameConfiguration;
import net.minecraft2.client.resources.DefaultResourcePack;
import net.minecraft2.client.settings.GameSettings;
import net.minecraft2.client.tutorial.Tutorial;
import net.minecraft2.client.util.Session;
import net.minecraft2.crash.CrashReport;
import net.minecraft2.init.Bootstrap;
import net.minecraft2.profiler.ISnooperInfo;
import net.minecraft2.profiler.Snooper;
import net.minecraft2.server.MinecraftServer;
import net.minecraft2.server.integrated.IntegratedServer;
import net.minecraft2.util.DataFixer;
import net.minecraft2.util.ResourceLocation;
import net.minecraft2.util.Util;
import net.minecraft2.util.datafix.DataFixesManager;

public class Minecraft implements ISnooperInfo {
	
	private static final Logger LOGGER = LogManager.getLogger();
	private static final ResourceLocation LOCATION_MOJANG_PNG = new ResourceLocation("textures/gui/title/mojang.png");
	public static final boolean IS_RUNNING_ON_PC = Util.getOSType() == Util.EnumOS.OSX;

	public static byte[] memoryReserve = new byte[10485760];
	private static final List<DisplayMode> MAC_DISPLAY_MODE = Lists.newArrayList(new DisplayMode(2560, 1600), new DisplayMode(2880, 1800));
	
	
	private final File fileResourcePacks;
	private final PropertyMap twitchDetails;
	private final PropertyMap profileProperties;
	
	private static Minecraft instance;
	private IntegratedServer theIntegratedServer;
	public final File mcDataDir;
	
	private final boolean enableGlErrorChecking = true; 
	//private final Timer timer = new Timer(20f);
	
	private final Snooper usageSnooper = new Snooper("client", this, MinecraftServer.getCurrentTimeMillis());
	
	private final Proxy proxy;
	private final DataFixer dataFixer;
	private final File fileAssets;
	private final String launchedVersion;
	private final String versionType;
	private final int tempDisplayWidth;
	private final int tempDisplayHeight;
	private final Session session;
	
	private final boolean jvm64bit;
	private final boolean isDemo;
	private final DefaultResourcePack mcDefaultResourcePack;
	
	private String serverName;
	private int serverPort;
	
	private final MinecraftSessionService sessionService;
	private final GuiToast guiToast;
	private final Tutorial tutorial;
	volatile boolean running;
	public GameSettings gameSettings;
	
	public Minecraft(GameConfiguration gc) {
		instance = this;
		mcDataDir = gc.folderInfo.mcDataDir;
		fileAssets = gc.folderInfo.assetsDir;
		fileResourcePacks = gc.folderInfo.resourcePacksDir;
		launchedVersion = gc.gameInfo.version;
		versionType = gc.gameInfo.versionType;
		twitchDetails = gc.userInfo.userProperties;
		profileProperties = gc.userInfo.profileProperties;
		mcDefaultResourcePack = new DefaultResourcePack(gc.folderInfo.getAssetsIndex());
		proxy = gc.userInfo.proxy == null ? Proxy.NO_PROXY : gc.userInfo.proxy;
		sessionService = (new YggdrasilAuthenticationService(proxy, UUID.randomUUID().toString())).createMinecraftSessionService();
		session = gc.userInfo.session;
		LOGGER.info("Setting user: {}",(Object)this.session.getUsername());
		LOGGER.debug("(Session ID is {}",(Object)this.session.getSessionID());
		isDemo = gc.gameInfo.isDemo;
		tempDisplayWidth = gc.displayInfo.width;
		tempDisplayHeight = gc.displayInfo.height;
		jvm64bit = isJvm64Bit();
		theIntegratedServer = null;
		if(gc.serverInfo.serverName != null) {
			this.serverName = gc.serverInfo.serverName;
			this.serverPort = gc.serverInfo.serverPort;
		}
		ImageIO.setUseCache(false);
		Locale.setDefault(Locale.ROOT);
		Bootstrap.register();
		//TextComponentKeybind a;
		guiToast = new GuiToast(this);
		tutorial = new Tutorial(this);
		dataFixer = DataFixesManager.createFixer();
	}
	
	private static boolean isJvm64Bit() {
		String[] astring = new String[] {"sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch"};
		for(String s : astring) {
			String s1 = System.getProperty(s);
			if(s1 != null && s1.contains("64")) {
				return true;
			}
		}
		return false;
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
	// TODO
	public void run() {
		this.running = true;
		try {
			this.startGame();
		}
		catch(Throwable throwable) {
			CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Initializing game");
			crashReport.makeCategory("Initialization");
			//this.displayCrashReport(this.addGraphicsAndWorldToCrashReport(crashReport));
			return;
		}
	}

	private void startGame() throws LWJGLException, IOException {
		this.gameSettings = new GameSettings(this, this.mcDataDir);
		
	}
}
