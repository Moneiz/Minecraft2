package net.minecraft2.util;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

public class ResourceLocation {
	protected final String resourceDomain;
	protected final String resourcePath;
	
	public ResourceLocation(String resourceName) {
		this(0, splitObjectName(resourceName));
	}

	public ResourceLocation(int i, String...resourceName) {
		this.resourceDomain = StringUtils.isEmpty(resourceName[0]) ? "minecraft" : resourceName[0].toLowerCase(Locale.ROOT);
		this.resourcePath = resourceName[1].toLowerCase(Locale.ROOT);
		Validate.notNull(this.resourcePath);
	}

	protected static String[] splitObjectName(String resourceName) {
		String[] result = new String[] {"minecraft", resourceName};
		int i = resourceName.indexOf(58);
		if(i >= 0) {
			result[1] = resourceName.substring(i+1, resourceName.length());
			if(i > 1) {
				result[0] = resourceName.substring(0, i);
			}
		}
		return result;
	}
}
