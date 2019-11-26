package net.minecraft2.crash;

import java.util.List;

import com.google.common.collect.Lists;

public class CrashReportCategory {

	private final CrashReport crashReport;
	private final String name;
	private final List<CrashReportCategory.Entry> children = Lists.<CrashReportCategory.Entry>newArrayList();
	private StackTraceElement[] stackTrace = new StackTraceElement[0];
	
 	public CrashReportCategory(CrashReport crashReport, String string) {
 		this.crashReport = crashReport;
 		this.name = string;
	}

	public void setDetail(String string, ICrashReportDetail<String> detail) {
		try {
			this.addCrashSection(string, detail.call());
		}catch (Throwable e) {
			this.addCrashSectionThrowable(string, e);
		}
	}
	public void addCrashSection(String sectionName, Object value) {
		this.children.add(new CrashReportCategory.Entry(sectionName, value));
	}
	public void addCrashSectionThrowable(String sectionName, Throwable throwable) {
		this.addCrashSection(sectionName, throwable);
	}
	static class Entry{
		private final String key;
		private final String value;
		public Entry(String key, Object value) {
			this.key = key;
			if(value == null) {
				this.value = "~~NULL~~";
			}
			else if(value instanceof Throwable) {
				Throwable throwable = (Throwable)value;
				this.value = "~~ERROR~~ " + throwable.getClass().getSimpleName()+": "+throwable.getMessage();
			}else {
				this.value = value.toString();
			}
		}
		public String getKey() {
			return key;
		}
		public String getValue() {
			return value;
		}
	}
	public int getPrunedStackTrace(int size) {
		StackTraceElement[] astacktraceelement = Thread.currentThread().getStackTrace();
		if(astacktraceelement.length <=0) {
			return 0;
		}
		else {
			this.stackTrace = new StackTraceElement[astacktraceelement.length - 3 - size];
			System.arraycopy(astacktraceelement, 3+size, this.stackTrace, 0, this.stackTrace.length);
			return this.stackTrace.length;
		}
	}

	public boolean firstTwoElementsOfStackTraceMatch(StackTraceElement s1,
			StackTraceElement s2) {
		if(this.stackTrace.length != 0 && s1 != null) {
			StackTraceElement stacktraceelement = this.stackTrace[0];
			if(stacktraceelement.isNativeMethod() == s1.isNativeMethod() && 
					stacktraceelement.getClassName().equals(s1.getClassName()) && 
					stacktraceelement.getFileName().equals(s1.getFileName()) &&
					stacktraceelement.getMethodName().equals(s1.getMethodName())){
				if(s2 != null != this.stackTrace.length > 1) {
					return false;
				}
				else if(s2 != null && !this.stackTrace[1].equals(s2)) {
					return false;
				}
				else {
					this.stackTrace[0] = s1;
					return true;
				}
			}else{
				return false;
			}
			
		}else {
			return false;
		}
	}

	public void trimStackTraceEntriesFromBottom(int amount) {
		
		StackTraceElement[] astacktraceelement = new StackTraceElement[this.stackTrace.length - amount];
		System.arraycopy(this.stackTrace, 0, astacktraceelement, 0, astacktraceelement.length);
		this.stackTrace = astacktraceelement;
		
	}
}
