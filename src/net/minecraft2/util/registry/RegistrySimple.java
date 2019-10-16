package net.minecraft2.util.registry;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Maps;

public class RegistrySimple<K,V> implements IRegistry<K, V> {

	private static final Logger LOGGER = LogManager.getLogger();
	protected final Map<K,V> registryObjects = this.createUnderlyingMap();
	private Object[] values;
	
	public Iterator<V> iterator() {
		return this.registryObjects.values().iterator();
	}

	protected Map<K, V> createUnderlyingMap() {
		return Maps.<K,V>newHashMap();
	}
	protected void putObject(K key, V value) {
		Validate.notNull(key);
		Validate.notNull(value);
		this.values = null;
		
		if(this.registryObjects.containsKey(key)) {
			LOGGER.debug("Ajout d'une cle dupliquee '{}' dans le registre", key);
		}
		this.registryObjects.put(key, value);
	}

}
