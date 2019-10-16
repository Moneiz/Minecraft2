package net.minecraft2.util;

import java.io.OutputStream;
import java.io.PrintStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggingPrintStream extends PrintStream{
	protected static final Logger LOGGER = LogManager.getLogger();
	protected final String domain;
	public LoggingPrintStream(String domain, OutputStream outStream) {
		super(outStream);
		this.domain = domain;
	}
	public void println(String msg) {
		this.logString(msg);
	}
	public void println(Object msg) {
		this.logString(String.valueOf(msg));
	}
	protected void logString(String string) {
		LOGGER.info("[{}]: {}",string);
	}
}
