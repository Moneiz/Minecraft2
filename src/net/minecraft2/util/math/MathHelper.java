package net.minecraft2.util.math;

public class MathHelper {
	public static int hash(int a) {
		a = a ^ a >>> 16;
		a = a * -2048144789;
		a = a ^ a >>> 13;
		a = a * -1028477387;
		a = a ^ a >>> 16;
		return a;
	}
}
