package net.minecraft2.util;

public class IntHashMap<V> {

	private transient IntHashMap.Entry<V>[] slots = new IntHashMap.Entry[16];
	private transient int count;
	private int threshold = 12;
	private final float growFactor = 0.75f;

	public void addKey(int hashEntry, V valueEntry) {
		int i = computeHash(hashEntry);
		int j = getSlotIndex(i, this.slots.length);
		
		for(IntHashMap.Entry<V> entry = this.slots[j]; entry != null ; entry = entry.nextEntry) {
			if(entry.hashEntry == hashEntry) {
				entry.valueEntry = valueEntry;
				return;
			}
		}
		this.insert(i, hashEntry, valueEntry, j);
	}

	private void insert(int hash, int hashEntry, V valueEntry, int index) {
		IntHashMap.Entry<V> entry = this.slots[index];
		this.slots[index] = new IntHashMap.Entry(hash, hashEntry, valueEntry, entry);
		if(this.count++ >= this.threshold ) {
			this.grow(2 * this.slots.length);
		}
	}

	private void grow(int size) {
		IntHashMap.Entry<V>[] entry = this.slots;
		int i = entry.length;
		
		if(i == 1073741824) {
			this.threshold = Integer.MAX_VALUE;
		}
		else {
			IntHashMap.Entry<V>[] entry1 = new IntHashMap.Entry[size];
			this.copyTo(entry1);
			this.slots = entry1;
			this.threshold = (int)((float) size * this.growFactor);
		}
	}

	private void copyTo(IntHashMap.Entry<V>[] entry) {
		
		IntHashMap.Entry<V>[] entries = this.slots;
		int i = entry.length;
		
		for(int j = 0; j < entry.length; ++j) {
			IntHashMap.Entry<V> entry1 = entries[j];
			if(entry1 != null) {
				entries[j] = null;
				while(true) {
					IntHashMap.Entry<V> entry2 = entry1.nextEntry;
					int k = getSlotIndex(entry1.slotHash, i);
					entry[k] = entry1;
					entry1 = entry2;
					if(entry2 == null) {
						break;
					}
				}
			}
		}
		
	}

	private static int getSlotIndex(int hash, int slotCount) {
		return hash & slotCount - 1;
	}

	private int computeHash(int integer) {
		integer = integer ^ integer >>> 20 ^ integer >>> 12;
		return integer ^ integer >>> 7 ^ integer >>> 4;
	}
	static class Entry<V>{
		final int hashEntry;
		V valueEntry;
		IntHashMap.Entry<V> nextEntry;
		final int slotHash;
		public Entry(int hash, int hashEntry, V valueEntry, Entry<V> entry) {
			this.hashEntry = hashEntry;
			this.slotHash = hash;
			this.valueEntry = valueEntry;
			this.nextEntry = entry;
		}
	}
}
