package net.minecraft2.block.properties;

import java.util.Collection;

public interface IProperty<T> extends Comparable<T> {

	String getName();

	Collection<T> getAllowedValues();

}
