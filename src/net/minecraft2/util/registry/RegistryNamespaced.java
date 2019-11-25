package net.minecraft2.util.registry;

import java.util.Iterator;
import java.util.Map;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import net.minecraft2.util.IObjectIntIterable;
import net.minecraft2.util.IntIdentityHashBiMap;
import net.minecraft2.util.ResourceLocation;
import net.minecraft2.util.SoundEvent;

public class RegistryNamespaced<K,V> extends RegistrySimple<K,V> implements IObjectIntIterable<V> {

	protected final IntIdentityHashBiMap<V> underlyingIntegerMap = new IntIdentityHashBiMap(256);
	protected final Map<V,K> inverseObjectRegistry;
	
	public RegistryNamespaced() {
		this.inverseObjectRegistry = ((BiMap) this.registryObjects).inverse();
	}
	
	public void register(int i, K key, V value) {
		this.underlyingIntegerMap.put(value, i);
		this.putObject(key, value);
	}
	
	protected Map<K, V> createUnderlyingMap() {
		return HashBiMap.<K,V>create();
	}
	
	public V getObject(K name) {
		return (V)super.getObject(name);
	}
	public Iterator<V> iterator(){
		return this.underlyingIntegerMap.iterator();
	}
	
}
