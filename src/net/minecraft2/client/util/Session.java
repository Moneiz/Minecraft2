package net.minecraft2.client.util;

import java.util.Locale;
import java.util.Map;

import com.google.common.collect.Maps;

public class Session {
	private final String username;
	private final String playerID;
	private final String token;
	private final Session.Type sessionType;
	public Session(String usernameIn, String playerIDIn, String tokenIn, String sessionTypeIn) {
		this.username = usernameIn;
		this.playerID = playerIDIn;
		this.token = tokenIn;
		this.sessionType = Session.Type.setSessionType(sessionTypeIn);
	}
	
	public static enum Type{
		LEGACY("legacy"),
		MOJANG("mojang");
		
		private static final Map<String, Session.Type> SESSION_TYPE = Maps.<String, Session.Type>newHashMap();
		private final String sessionType;
		
		private Type(String sessionTypeIn) {
			this.sessionType = sessionTypeIn;
		}
		public static Session.Type setSessionType(String sessionTypeIn){
			return SESSION_TYPE.get(sessionTypeIn.toLowerCase(Locale.ROOT));
		}
		static {
			for(Session.Type session$type : values()) {
				SESSION_TYPE.put(session$type.sessionType, session$type);
			}
		}
	}
}
