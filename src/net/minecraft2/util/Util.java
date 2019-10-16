package net.minecraft2.util;

import java.util.Locale;

public class Util {

	public static enum EnumOS{
		LINUX, 
		SOLARIS, 
		WINDOWS, 
		OSX,
		UNKNOWN;
	}
	
	public static Util.EnumOS getOSType() {

		String s = System.getProperty("os.name").toLowerCase(Locale.ROOT);
		if(s.contains("win")) {
			return Util.EnumOS.WINDOWS;
		}
		else if(s.contains("mac")) {
			return Util.EnumOS.OSX;
		}else if(s.contains("solaris")) {
			return Util.EnumOS.SOLARIS;
		}else if(s.contains("sunos")) {
			return Util.EnumOS.SOLARIS;
		}else if(s.contains("linux")) {
			return Util.EnumOS.LINUX;
		}else {
			return s.contains("unix") ? Util.EnumOS.LINUX : Util.EnumOS.UNKNOWN;
		}
	}

}
