package net.minecraft2.block.state;

import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.UnmodifiableIterator;

import net.minecraft2.block.Block;
import net.minecraft2.block.properties.IProperty;
import net.minecraft2.common.property.IUnlistedProperty;
import net.minecraft2.util.math.Cartesian;

public class BlockStateContainer {
	
	private final Block block;
	private final ImmutableSortedMap<String, IProperty<?>>properties;
	//private final ImmutableList<IBlockState> validStates;
	
	public BlockStateContainer(Block block, IProperty<?>...properties) {
		this(block, properties,(ImmutableMap)null);
	}

	protected BlockStateContainer(Block block, IProperty<?>[] properties, ImmutableMap<IUnlistedProperty<?>, Optional<?>> immutableMap) {
		this.block = block;
		Map<String, IProperty<?>> map = Maps.<String, IProperty<?>>newHashMap();
		
		for(IProperty<?> iproperty : properties) {
			//validateProperty(block, iproperty);
			map.put(iproperty.getName(), iproperty);
		}
		this.properties = ImmutableSortedMap.copyOf(map);
		Map<Map<IProperty<?>, Comparable<?>>, BlockStateContainer.StateImplementation> map2 = Maps.<Map<IProperty<?>, Comparable<?>>, BlockStateContainer.StateImplementation>newLinkedHashMap();
		List<BlockStateContainer.StateImplementation> list = Lists.<BlockStateContainer.StateImplementation>newArrayList() ;
		
		for(List<Comparable<?>> list1: Cartesian.cartesianProduct(this.getAllowedValues()) ) {
			
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
	
	public class StateImplementation{
		
	}
}
