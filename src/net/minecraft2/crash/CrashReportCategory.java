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
}
