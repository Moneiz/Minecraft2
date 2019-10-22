package net.minecraft2.block.state;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import com.google.common.base.Optional;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.collect.UnmodifiableIterator;

import net.minecraft2.block.Block;
import net.minecraft2.block.properties.IProperty;
import net.minecraft2.common.property.IUnlistedProperty;
import net.minecraft2.util.MapPopular;
import net.minecraft2.util.math.Cartesian;

public class BlockStateContainer {
	
	private static final Pattern NAME_PATTERN = Pattern.compile("^[a-z0-9_]+$");
	private final Block block;
	private final ImmutableSortedMap<String, IProperty<?>>properties;
	private final ImmutableList<IBlockState> validStates;

	public BlockStateContainer(Block block, IProperty<?>[] properties) {
		this.block = block;
		Map<String, IProperty<?>> map = Maps.<String, IProperty<?>>newHashMap();
		
		for(IProperty<?> iproperty : properties) {
			validateProperty(block, iproperty);
			map.put(iproperty.getName(), iproperty);
		}
		this.properties = ImmutableSortedMap.copyOf(map);
		Map<Map<IProperty<?>, Comparable<?>>, BlockStateContainer.StateImplementation> map2 = Maps.<Map<IProperty<?>, Comparable<?>>, BlockStateContainer.StateImplementation>newLinkedHashMap();
		List<BlockStateContainer.StateImplementation> list = Lists.<BlockStateContainer.StateImplementation>newArrayList() ;
		
		for(List<Comparable<?>> list1: Cartesian.cartesianProduct(this.getAllowedValues()) ) {
			Map<IProperty<?>,Comparable<?>> map1 = MapPopular. <IProperty<?>,Comparable<?>> createMap((Iterable)this.properties.values(), (Iterable)list);
			BlockStateContainer.StateImplementation stateimple = new BlockStateContainer.StateImplementation(block, ImmutableMap.copyOf(map1));
			map2.put(map1, stateimple);
			list.add(stateimple);
		}
		for(BlockStateContainer.StateImplementation blockImplementation1 : list) {
			blockImplementation1.buildPropertyValueTable(map2);
		}
		this.validStates = ImmutableList.<IBlockState>copyOf(list);
	}
	
	private static <T extends Comparable<T>> String validateProperty(Block block, IProperty<?> property) {
		String s = property.getName();
		if(!NAME_PATTERN.matcher(s).matches()) {
			throw new IllegalArgumentException("Bloc: " + block.getClass()+" a une nom de propriete incorrect: " +s);
		}
		else {
			for(T t : (Collection<T>)property.getAllowedValues()) {
				String s1 = property.getName();
				
				if(!NAME_PATTERN.matcher(s1).matches()) {
					throw new IllegalArgumentException("Bloc: " + block.getClass()+ " a la propriete:" +s +" avec un nom incorrect:" + s1);
				}
			}
			return s;
		}
		
	}

	private List<Iterable<Comparable<?>>> getAllowedValues(){
		List<Iterable<Comparable<?>>> list = Lists.<Iterable<Comparable<?>>> newArrayList();
		ImmutableCollection<IProperty<?>> immutableCollection= this.properties.values();
		UnmodifiableIterator unmodifiableIterator = immutableCollection.iterator();
		
		while(unmodifiableIterator.hasNext()) {
			IProperty<?> iproperty = (IProperty)unmodifiableIterator.next();
			list.add(((IProperty)iproperty).getAllowedValues());
		}
		return list;
	}
	
	public class StateImplementation extends BlockStateBase{
		private final Block block;
		private final ImmutableMap<IProperty<?>, Comparable<?>> properties;
		private ImmutableTable<IProperty<?>, Comparable<?>, IBlockState> propertyValueTable;
		private StateImplementation(Block block, ImmutableMap<IProperty<?>, Comparable<?>> properties) {
			this.block = block;
			this.properties = properties;
		}
		public void buildPropertyValueTable(Map<Map<IProperty<?>, Comparable<?>>, StateImplementation> map) {
			if(this.propertyValueTable != null) {
				throw new IllegalStateException();
			}
			else {
				Table<IProperty<?>, Comparable<?>, IBlockState> table = HashBasedTable. <IProperty<?>, Comparable<?>, IBlockState> create();
				UnmodifiableIterator unmodifiableiterator = this.properties.entrySet().iterator();
				
				while(unmodifiableiterator.hasNext()) {
					Entry<IProperty<?>, Comparable<?>> entry = (Entry) unmodifiableiterator.next();
					IProperty<?> iprop = (IProperty) entry.getKey();
					for(Comparable<?> comparable : (Collection<Comparable<?>>)iprop.getAllowedValues()) {
						if(comparable != entry.getValue()) {
							table.put(iprop, comparable, map.get(this.getPropertiesWithValue(iprop, comparable)));
						}
					}
				}
				this.propertyValueTable = ImmutableTable.copyOf(table);
			}
			
		}
		private Map<IProperty<?>, Comparable<?>> getPropertiesWithValue(IProperty<?> prop, Comparable<?> value) {
			Map<IProperty<?>, Comparable<?>> map = Maps. <IProperty<?>, Comparable<?>> newHashMap(this.properties);
			map.put(prop, value);
			return map;
		}
	}

	public IBlockState getBaseState() {
		return (IBlockState) this.validStates.get(0);
	}
}
