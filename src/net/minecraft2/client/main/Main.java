package net.minecraft2.client.main;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.properties.PropertyMap.Serializer;

import java.io.File;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.Proxy.Type;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import net.minecraft2.client.Minecraft;
import net.minecraft2.client.util.JsonUtils;
import net.minecraft2.client.util.Session;

public class Main {
	public static void main(String[] args) {
		OptionParser optionparser = new OptionParser();
		optionparser.allowsUnrecognizedOptions();
		optionparser.accepts("demo");
		optionparser.accepts("fullscreen");
		optionparser.accepts("checkGlErrors");
		OptionSpec<String> optionspec = optionparser.accepts("server").withRequiredArg();
		OptionSpec<Integer> optionspec1 = optionparser.accepts("port").withRequiredArg().<Integer>ofType(Integer.class).defaultsTo(Integer.valueOf(25565));
		OptionSpec<File> optionspec2 = optionparser.accepts("gameDir").withRequiredArg().<File>ofType(File.class).defaultsTo(new File("."));
		OptionSpec<File> optionspec3 = optionparser.accepts("assetsDir").withRequiredArg().<File>ofType(File.class);
		OptionSpec<File> optionspec4 = optionparser.accepts("resourcePackDir").withRequiredArg().<File>ofType(File.class);
		OptionSpec<String> optionspec5 = optionparser.accepts("proxyHost").withRequiredArg();
		OptionSpec<Integer> optionspec6 = optionparser.accepts("proxyPort").withRequiredArg().defaultsTo("8080").<Integer>ofType(Integer.class);
		OptionSpec<String> optionspec7 = optionparser.accepts("proxyUser").withRequiredArg();
		OptionSpec<String> optionspec8 = optionparser.accepts("proxyPass").withRequiredArg();
		OptionSpec<String> optionspec9 = optionparser.accepts("username").withRequiredArg().defaultsTo("Player"+ Minecraft.getSystemTime() % 1000L);
		OptionSpec<String> optionspec10 = optionparser.accepts("uuid").withRequiredArg();
		OptionSpec<String> optionspec11 = optionparser.accepts("accessToken").withRequiredArg().required();
		OptionSpec<String> optionspec12 = optionparser.accepts("version").withRequiredArg().required();
		OptionSpec<Integer> optionspec13 = optionparser.accepts("width").withRequiredArg().<Integer>ofType(Integer.class).defaultsTo(Integer.valueOf(854));
		OptionSpec<Integer> optionspec14 = optionparser.accepts("height").withRequiredArg().<Integer>ofType(Integer.class).defaultsTo(Integer.valueOf(480));
		OptionSpec<String> optionspec15 = optionparser.accepts("userProperties").withRequiredArg().defaultsTo("{}");
		OptionSpec<String> optionspec16 = optionparser.accepts("profileProperties").withRequiredArg().defaultsTo("{}");
		OptionSpec<String> optionspec17 = optionparser.accepts("assetIndex").withRequiredArg();
		OptionSpec<String> optionspec18 = optionparser.accepts("userType").withRequiredArg().defaultsTo("legacy");
		OptionSpec<String> optionspec19 = optionparser.accepts("versionType").withRequiredArg().defaultsTo("release");
		OptionSpec<String> optionspec20 = optionparser.nonOptions();
		OptionSet optionset = optionparser.parse(args);
		List<String> list = optionset.valuesOf(optionspec20);
		
		if(!list.isEmpty()) {
			System.out.println("Arguments ignores: " + list);
		}
		
		String hostname = (String) optionset.valueOf(optionspec5);
		Proxy proxy = Proxy.NO_PROXY;
		
		if(hostname != null) {
			try {
				proxy = new Proxy(Type.SOCKS, new InetSocketAddress(hostname, ((Integer)optionset.valueOf(optionspec6)).intValue()));
				
			}catch (Exception ex) {
				;
			}
		}
		
		final String proxyUser = (String)optionset.valueOf(optionspec7);
		final String proxyPass = (String)optionset.valueOf(optionspec8);
		
		if(!proxy.equals(Proxy.NO_PROXY) && isNullOrEmpty(proxyUser) && isNullOrEmpty(proxyPass)) {
			Authenticator.setDefault(new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(proxyUser, proxyPass.toCharArray());
				}
			});
		}
		int width = ((Integer)optionset.valueOf(optionspec13).intValue());
		int height = ((Integer)optionset.valueOf(optionspec14).intValue());
		boolean flag = optionset.has("fullscreen");
		boolean flag1 = optionset.has("checkGlErrors");
		boolean flag2 = optionset.has("demo");
		String version = (String)optionset.valueOf(optionspec12);
		Gson gson = (new GsonBuilder()).registerTypeAdapter(PropertyMap.class, new Serializer()).create();
		PropertyMap propertyMap = (PropertyMap) JsonUtils.gsonDeserialize(gson, (String)optionset.valueOf(optionspec15), PropertyMap.class);
		PropertyMap propertyMap1 = (PropertyMap) JsonUtils.gsonDeserialize(gson, (String)optionset.valueOf(optionspec15), PropertyMap.class);
		String versionType = (String)optionset.valueOf(optionspec19);
		File gameDir = (File)optionset.valueOf(optionspec2);
		File assetsDir = optionset.has(optionspec3) ? (File)optionset.valueOf(optionspec3) : new File(gameDir, "assets/");
		File resourcesPackDir = optionset.has(optionspec4) ? (File)optionset.valueOf(optionspec4) : new File(gameDir, "resourcepacks/");
		String uuid = optionset.has(optionspec10) ? (String) optionspec10.value(optionset) : (String)optionspec9.value(optionset) ;
		String assetIndex = optionset.has(optionspec17) ? (String) optionspec17.value(optionset) : null;
		String server = (String) optionset.valueOf(optionspec);
		Integer port = (Integer)optionset.valueOf(optionspec1);
		Session session = new Session(optionspec9.value(optionset),uuid, optionspec11.value(optionset), optionspec18.value(optionset));
		GameConfiguration gameconfig = new GameConfiguration(
				new GameConfiguration.UserInformation(session, propertyMap,propertyMap1,proxy),
				new GameConfiguration.DisplayInformation(width, height, flag, flag1),
				new GameConfiguration.FolderInformation(gameDir, resourcesPackDir, assetsDir,assetIndex),
				new GameConfiguration.GameInformation(flag2, version,versionType),
				new GameConfiguration.ServerInformation(server, port.intValue()));
		Runtime.getRuntime().addShutdownHook(new Thread("Processus d'extinction") {
			public void run() {
				Minecraft.stopIntegratedServer();
			}
		});
		Thread.currentThread().setName("Processus principal");
		(new Minecraft(gameconfig)).run();
	}
	private static boolean isNullOrEmpty(String str) {
		return str != null && str.isEmpty();
	}
}
