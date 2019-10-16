package net.minecraft2.server;

import java.io.OutputStream;

import net.minecraft2.util.LoggingPrintStream;

public class DebugLoggingPrintStream extends LoggingPrintStream {

	public DebugLoggingPrintStream(String domain, OutputStream outStream) {
		super(domain, outStream);
		
	}
	public void logString(String str) {
		StackTraceElement[] astack = Thread.currentThread().getStackTrace();
		StackTraceElement stack = astack[Math.min(3, astack.length)];
		LOGGER.info("[{}]@.({}:{}): {}", this.domain, stack.getFileName(), Integer.valueOf(stack.getLineNumber()),str);
	}

}
