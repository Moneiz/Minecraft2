package net.minecraft2.util;

import net.minecraft2.crash.CrashReport;

public class ReportedException extends RuntimeException {
	
	private final CrashReport crashReport;
	
	public ReportedException(CrashReport report) {
		this.crashReport =report;
	}
	public CrashReport getCrashReport() {
		return this.crashReport;
	}
}
