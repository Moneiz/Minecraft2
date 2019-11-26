package net.minecraft2.crash;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.Sys;

import com.google.common.collect.Lists;

import net.minecraft2.util.ReportedException;
import net.minecraft2.world.gen.layer.IntCache.IntCache;

public class CrashReport {
	private static final Logger LOGGER = LogManager.getLogger();
	
	private final String description;
	
	private final Throwable cause;
	
	private final CrashReportCategory theReportCategory = new CrashReportCategory(this, "System Details");
	private final List<CrashReportCategory> crashReportSections = Lists.<CrashReportCategory>newArrayList();
	
	private File crashReportFile;
	
	private boolean firstCategoryInCrashReport = true;
	private StackTraceElement[] stacktrace = new StackTraceElement[0];
	private boolean reported = false;
	
	public CrashReport(String description, Throwable causeThrowable) {
		this.description = description;
		this.cause = causeThrowable;
		this.populateEnvironement();
	}

	private void populateEnvironement() {
		this.theReportCategory.setDetail("Minecraft Version", new ICrashReportDetail<String>() {
			public String call() {
				return "1.12";
			}
		});
		this.theReportCategory.setDetail("OS", new ICrashReportDetail<String>() {
			
			public String call(){
				return System.getProperty("os.name") + " ("+System.getProperty("os.arch")+") version "+System.getProperty("os.version");
			}
		});
		this.theReportCategory.setDetail("Java Version", new ICrashReportDetail<String>() {
			
			public String call() {
				return System.getProperty("java.version") + ", "+ System.getProperty("java.vendor");
			}
		});
		this.theReportCategory.setDetail("Java VM Version", new ICrashReportDetail<String>() {
			
			public String call() {
				return System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor");
			}
		});
		this.theReportCategory.setDetail("Memory", new ICrashReportDetail<String>() {
			
			public String call() {
				Runtime runtime = Runtime.getRuntime();
				long i = runtime.maxMemory();
				long j = runtime.totalMemory();
				long k = runtime.freeMemory();
				long l = i / 1024L / 1024L;
				long i1 = j / 1024L / 1024L;
				long j1 = k / 1024L / 1024L;
				return k + " bytes (" + j1 + " MB) / " + j + " bytes (" + i1 + " MB) up to "+ i + " bytes ("+l+" MB)";
			}
		});
		this.theReportCategory.setDetail("JVM Flags", new ICrashReportDetail<String>() {
			
			public String call() throws Exception {
				RuntimeMXBean runtimemxbean = ManagementFactory.getRuntimeMXBean();
				List<String> list = runtimemxbean.getInputArguments();
				StringBuilder stringbuilder = new StringBuilder();
				int i = 0;
				for(String s : list) {
					if(s.startsWith("-X")) {
						if(i++>0) {
							stringbuilder.append(" ");
						}
						stringbuilder.append(s);
					}
				}
				return String.format("%d total; %s", i, stringbuilder.toString());
			}
		});
		this.theReportCategory.setDetail("IntCache", new ICrashReportDetail<String>() {
			
			public String call() throws Exception {
				return IntCache.getCacheSizes();
			}
		});
	}

	public static CrashReport makeCrashReport(Throwable cause, String description) {
		CrashReport crashReport;
		if(cause instanceof ReportedException) {
			crashReport = ((ReportedException)cause).getCrashReport();
		}else {
			crashReport = new CrashReport(description, cause);
		}
		return crashReport;
	}

	public CrashReportCategory makeCategory(String string) {
		return this.makeCategoryDepth(string, 1);
		
	}

	public CrashReportCategory makeCategoryDepth(String category, int stacktraceLength) {
		CrashReportCategory crashreportcategory = new CrashReportCategory(this, category);
		if(this.firstCategoryInCrashReport) {
			int i = crashreportcategory.getPrunedStackTrace(stacktraceLength);
			StackTraceElement[] astacktraceelement = this.cause.getStackTrace();
			StackTraceElement stacktraceelement = null;
			StackTraceElement stacktraceelement1 = null;
			int j = astacktraceelement.length - i;
			if (j < 0) {
				System.out.println("Indice négatif dans la rédaction du rapport de crash (" + astacktraceelement.length + "/" + i + ")");
			}
			if(astacktraceelement != null && 0 <= j && j <astacktraceelement.length) {
				stacktraceelement = astacktraceelement[j];
				if(astacktraceelement.length +1 - i < astacktraceelement.length) {
					stacktraceelement1 = astacktraceelement[astacktraceelement.length + 1 - i];
				}
			}
			this.firstCategoryInCrashReport = crashreportcategory.firstTwoElementsOfStackTraceMatch(stacktraceelement, stacktraceelement1);
			if (i > 0 && !this.crashReportSections.isEmpty()) {
				CrashReportCategory crashreportcategory1  = this.crashReportSections.get(this.crashReportSections.size() -1 );
				crashreportcategory1.trimStackTraceEntriesFromBottom(i);
			}
		}
	}
	
}
