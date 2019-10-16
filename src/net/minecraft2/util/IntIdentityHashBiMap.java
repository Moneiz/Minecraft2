package net.minecraft2.util;

import java.util.Iterator;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;

import net.minecraft2.util.math.MathHelper;

public class IntIdentityHashBiMap<K> implements IObjectIntIterable<K> {

	private static final Object EMPTY = null;
	private K[] byId;
	private K[] values;
	private int[] intKeys;
	private int mapSize;
	private int nextFreeIndex;
	
	public IntIdentityHashBiMap(int i) {
		i = (int)((float) i/0.8f);
		this.values = (K[])(new Object[i]);
		this.intKeys = new int[i];
		this.byId = (K[])(new Object[i]);
	}

	@Override
	public Iterator<K> iterator() {
		return Iterators.filter(Iterators.forArray(this.byId), Predicates.notNull());
	}

	public void put(K obj, int key) {
		int i = Math.max(key, this.mapSize+1);
		if((float)i >= (float)this.values.length * 0.8f) {
			int j;
			for(j = this.values.length << 1;j < key;j <<= 1) {
				;
			}
			this.grow(j);
		}
		int k = this.findEmpty(this.hashObject(obj));
		this.values[k] = obj;
		this.intKeys[k] = key;
		this.byId[key] = obj;
		++this.mapSize;
		
		if(key == this.nextFreeIndex) {
			++this.nextFreeIndex;
		}
	}

	private int findEmpty(int hash) {
		for(int i = hash; i < this.values.length;i++) {
			if(this.values[i] == EMPTY) {
				return i;
			}
		}
		for(int j = 0 ; j < hash; j++) {
			if(this.values[j] == EMPTY) {
				return j;
			}
		}
		throw new RuntimeException("Debordement");
	}

	private int hashObject( K obj) {
		return (MathHelper.hash(System.identityHashCode(obj)) & Integer.MAX_VALUE) % this.values.length;
	}

	private void grow(int capacity) {
		K[] ak = this.values;
		int[] aint = this.intKeys;
		this.values = (K[])(new Object[capacity]);
		this.intKeys = new int[capacity];
		this.byId = (K[]) (new Object[capacity]);
		this.nextFreeIndex = 0;
		this.mapSize = 0;
		
		for(int i = 0; i < ak.length;i++) {
			if(ak[i] != null) {
				this.put(ak[i], aint[i]);
			}
		}
	}

}
