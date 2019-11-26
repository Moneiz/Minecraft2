package net.minecraft2.world.gen.layer.IntCache;

import java.util.List;

import com.google.common.collect.Lists;

public class IntCache {
	private static int intCacheSize = 256;
	private static final List<int[]> freeSmallArrays = Lists.<int[]>newArrayList();
	private static final List<int[]> inUseSmallArrays = Lists.<int[]>newArrayList();
	private static final List<int[]> freeLargeArrays = Lists.<int[]>newArrayList();
	private static final List<int[]> inUseLargeArrays = Lists.<int[]>newArrayList();
	
	public static synchronized String getCacheSizes() {
		return "cache: " + freeLargeArrays.size() + ", tcache: " + freeSmallArrays.size() + ", allocated: " + inUseLargeArrays.size() + ", tallocated:" + inUseSmallArrays.size();
	}
}
