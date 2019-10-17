package net.minecraft2.util.registry;

import net.minecraft2.util.IObjectIntIterable;
import net.minecraft2.util.IntIdentityHashBiMap;
import net.minecraft2.util.ResourceLocation;
import net.minecraft2.util.SoundEvent;

public class RegistryNamespaced<K,V> extends RegistrySimple<K,V> implements IObjectIntIterable<V> {

	protected final IntIdentityHashBiMap<V> underlyingIntegerMap = new IntIdentityHashBiMap(256);
	
	public void register(int i, K key, V value) {
		this.underlyingIntegerMap.put(value, i);
		this.putObject(key, value);
	}

	public V getObject(K name) {
		return (V)super.getObject(name);
	}

}
