package net.minecraft2.util.registry;

public class RegistryNamespacedDefaultedByKey<K,V> extends RegistryNamespaced<K, V> {

	private final K defaultValueKey;
	private V defaultValue;
	
	
	public RegistryNamespacedDefaultedByKey(K defaultValueKey) {
		this.defaultValueKey = defaultValueKey;
	}
	
	public void register(int id, K key, V value) {
		if(this.defaultValueKey.equals(key)) {
			this.defaultValue = value;
		}
		
		super.register(id, key, value);
	}
}
