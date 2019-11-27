package net.minecraft2.client.resources;

public class I18n {
	private static Locale i18nLocale;
	
	static void setLocale(Locale _i18nLocale) {
		i18nLocale = _i18nLocale;
	}
	public static String format(String translateKey,Object...parameters) {
		return i18nLocale.formatMessage(translateKey, parameters);
	}
}
