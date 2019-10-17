package net.minecraft2.init;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft2.server.DebugLoggingPrintStream;
import net.minecraft2.util.LoggingPrintStream;
import net.minecraft2.util.SoundEvent;

public class Bootstrap {

	private static final Logger LOGGER = LogManager.getLogger();
	private static boolean alreadyRegistered;
	
	public static void register() {
		if(!alreadyRegistered) {
			alreadyRegistered = true;
			redirectOutputToLog();
			SoundEvent.registerSounds();
		}
	}

	private static void redirectOutputToLog() {
		if(LOGGER.isDebugEnabled()) {
			System.setErr(new DebugLoggingPrintStream("STDERR", System.err));
			System.setOut(new DebugLoggingPrintStream("STDOUT", System.out));
		}else {
			System.setErr(new LoggingPrintStream("STDERR", System.err));
			System.setOut(new LoggingPrintStream("STDOUT", System.out));
		}
	}

	public static boolean isRegistered() {
		return alreadyRegistered;
	}

}
